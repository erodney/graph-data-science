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
package positive;

import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;
import org.jetbrains.annotations.NotNull;
import org.neo4j.graphalgo.core.CypherMapWrapper;

import javax.annotation.processing.Generated;

@Generated("org.neo4j.graphalgo.proc.ConfigurationProcessor")
public final class ConversionsConfig implements Conversions.MyConversion {

    private final int directMethod;

    private final int inheritedMethod;

    private final int qualifiedMethod;

    private final String referenceTypeAsResult;

    public ConversionsConfig(@NotNull CypherMapWrapper config) {
        this.directMethod = Conversions.MyConversion.toInt(config.requireString("directMethod"));
        this.inheritedMethod = Conversions.BaseConversion.toIntBase(config.requireString("inheritedMethod");
        this.qualifiedMethod = Conversions.OtherConversion.toIntQual(config.requireString("qualifiedMethod");
        this.referenceTypeAsResult = CypherMapWrapper.failOnNull(
            "referenceTypeAsResult",
            Conversions.MyConversion.add42(config.requireString("referenceTypeAsResult"))
        );
    }

    public static Pair<Conversions.MyConversion, CypherMapWrapper> of(@NotNull CypherMapWrapper config) {
        Conversions.MyConversion instance = new ConversionsConfig(config);
        CypherMapWrapper newConfig = config.withoutAny("directMethod", "inheritedMethod", "qualifiedMethod", "referenceTypeAsResult");
        return Tuples.pair(instance, newConfig);
    }

    @Override
    public int directMethod() {
        return this.directMethod;
    }

    @Override
    public int inheritedMethod() {
        return this.inheritedMethod;
    }

    @Override
    public int qualifiedMethod() {
        return this.qualifiedMethod;
    }

    @Override
    public String referenceTypeAsResult() {
        return this.referenceTypeAsResult;
    }
}
