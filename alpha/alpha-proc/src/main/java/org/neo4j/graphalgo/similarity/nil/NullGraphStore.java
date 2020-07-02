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
package org.neo4j.graphalgo.similarity.nil;

import org.neo4j.graphalgo.NodeLabel;
import org.neo4j.graphalgo.RelationshipType;
import org.neo4j.graphalgo.api.Graph;
import org.neo4j.graphalgo.api.GraphStore;
import org.neo4j.graphalgo.api.NodeMapping;
import org.neo4j.graphalgo.api.NodeProperties;
import org.neo4j.graphalgo.api.schema.GraphStoreSchema;
import org.neo4j.graphalgo.core.huge.HugeGraph;
import org.neo4j.graphalgo.core.loading.DeletionResult;
import org.neo4j.values.storable.NumberType;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class NullGraphStore implements GraphStore {

    @Override
    public GraphStoreSchema schema() {
        return null;
    }

    @Override
    public ZonedDateTime modificationTime() {
        return null;
    }

    @Override
    public long nodeCount() {
        return 0;
    }

    @Override
    public NodeMapping nodes() {
        return null;
    }

    @Override
    public Set<NodeLabel> nodeLabels() {
        return Set.of();
    }

    @Override
    public Set<String> nodePropertyKeys(NodeLabel label) {
        return null;
    }

    @Override
    public Map<NodeLabel, Set<String>> nodePropertyKeys() {
        return null;
    }

    @Override
    public long nodePropertyCount() {
        return 0;
    }

    @Override
    public boolean hasNodeProperty(Collection<NodeLabel> labels, String propertyKey) {
        return false;
    }

    @Override
    public NumberType nodePropertyType(NodeLabel label, String propertyKey) {
        return null;
    }

    @Override
    public PropertyState nodePropertyState(String propertyKey) {
        return null;
    }

    @Override
    public NodeProperties nodePropertyValues(String propertyKey) {
        return null;
    }

    @Override
    public NodeProperties nodePropertyValues(NodeLabel label, String propertyKey) {
        return null;
    }

    @Override
    public void addNodeProperty(
        NodeLabel nodeLabel, String propertyKey, NumberType propertyType, NodeProperties propertyValues
    ) {

    }

    @Override
    public void removeNodeProperty(NodeLabel nodeLabel, String propertyKey) {

    }

    @Override
    public long relationshipCount() {
        return 0;
    }

    @Override
    public long relationshipCount(RelationshipType relationshipType) {
        return 0;
    }

    @Override
    public Set<RelationshipType> relationshipTypes() {
        return Set.of();
    }

    @Override
    public boolean hasRelationshipType(RelationshipType relationshipType) {
        return false;
    }

    @Override
    public boolean hasRelationshipProperty(Collection<RelationshipType> relTypes, String propertyKey) {
        return false;
    }

    @Override
    public NumberType relationshipPropertyType(String propertyKey) {
        return null;
    }

    @Override
    public long relationshipPropertyCount() {
        return 0;
    }

    @Override
    public Set<String> relationshipPropertyKeys() {
        return null;
    }

    @Override
    public Set<String> relationshipPropertyKeys(RelationshipType relationshipType) {
        return null;
    }

    @Override
    public void addRelationshipType(
        RelationshipType relationshipType,
        Optional<String> relationshipPropertyKey,
        Optional<NumberType> relationshipPropertyType,
        HugeGraph.Relationships relationships
    ) {

    }

    @Override
    public DeletionResult deleteRelationships(RelationshipType relationshipType) {
        return null;
    }

    @Override
    public Graph getGraph(
        Collection<NodeLabel> nodeLabels,
        Collection<RelationshipType> relationshipTypes,
        Optional<String> maybeRelationshipProperty
    ) {
        return new NullGraph();
    }

    @Override
    public Graph getUnion() {
        return new NullGraph();
    }

    @Override
    public void canRelease(boolean canRelease) {

    }

    @Override
    public void release() {

    }
}
