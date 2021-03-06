/*
 * Copyright (c) 2017-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.graphalgo.beta.pregel;

import org.neo4j.graphalgo.api.NodeProperties;
import org.neo4j.graphalgo.api.NodePropertyContainer;

import java.util.Set;

public abstract class PregelContext<CONFIG extends PregelConfig> {

    final Pregel.ComputeStep<CONFIG> computeStep;
    private final CONFIG config;

    public static <CONFIG extends PregelConfig> InitContext<CONFIG> initContext(
        Pregel.ComputeStep<CONFIG> computeStep,
        CONFIG config,
        NodePropertyContainer nodePropertyContainer
    ) {
        return new InitContext<>(computeStep, config, nodePropertyContainer);
    }

    public static <CONFIG extends PregelConfig> ComputeContext<CONFIG> computeContext(
        Pregel.ComputeStep<CONFIG> computeStep,
        CONFIG config
    ) {
        return new ComputeContext<>(computeStep, config);
    }

    PregelContext(Pregel.ComputeStep<CONFIG> computeStep, CONFIG config) {
        this.computeStep = computeStep;
        this.config = config;
    }

    public CONFIG getConfig() {
        return config;
    }

    public void setNodeValue(String key, long nodeId, double value) {
        computeStep.setNodeValue(key, nodeId, value);
    }

    public void setNodeValue(String key, long nodeId, long value) {
        computeStep.setNodeValue(key, nodeId, value);
    }

    public long getNodeCount() {
        return computeStep.getNodeCount();
    }

    public long getRelationshipCount() {
        return computeStep.getRelationshipCount();
    }

    public int getDegree(long nodeId) {
        return computeStep.getDegree(nodeId);
    }

    public static class InitContext<CONFIG extends PregelConfig> extends PregelContext<CONFIG> {
        private final NodePropertyContainer nodePropertyContainer;

        InitContext(
            Pregel.ComputeStep<CONFIG> computeStep,
            CONFIG config,
            NodePropertyContainer nodePropertyContainer
        ) {
            super(computeStep, config);
            this.nodePropertyContainer = nodePropertyContainer;
        }

        public Set<String> nodePropertyKeys() {
            return this.nodePropertyContainer.availableNodeProperties();
        }

        public NodeProperties nodeProperties(String key) {
            return this.nodePropertyContainer.nodeProperties(key);
        }
    }

    public static class ComputeContext<CONFIG extends PregelConfig> extends PregelContext<CONFIG> {

        ComputeContext(Pregel.ComputeStep<CONFIG> computeStep, CONFIG config) {
            super(computeStep, config);
            this.sendMessageFunction = config.relationshipWeightProperty() == null
                ? computeStep::sendMessages
                : computeStep::sendWeightedMessages;
        }

        private final SendMessageFunction sendMessageFunction;

        public double doubleNodeValue(String key, long nodeId) {
            return computeStep.doubleNodeValue(key, nodeId);
        }

        public long longNodeValue(String key, long nodeId) {
            return computeStep.longNodeValue(key, nodeId);
        }

        public void voteToHalt(long nodeId) {
            computeStep.voteToHalt(nodeId);
        }

        public boolean isInitialSuperstep() {
            return getSuperstep() == 0;
        }

        public int getSuperstep() {
            return computeStep.getIteration();
        }

        public void sendMessages(long nodeId, double message) {
            sendMessageFunction.sendMessage(nodeId, message);
        }

        @FunctionalInterface
        interface SendMessageFunction {

            void sendMessage(long nodeId, double message);
        }

    }
}
