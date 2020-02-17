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
package org.neo4j.graphalgo.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.graphalgo.BaseProcTest;
import org.neo4j.graphalgo.TestDatabaseCreator;
import org.neo4j.graphalgo.catalog.GraphCreateProc;
import org.neo4j.graphalgo.core.loading.GraphCatalog;
import org.neo4j.graphalgo.pagerank.PageRankStreamProc;
import org.neo4j.graphalgo.pagerank.PageRankWriteProc;
import org.neo4j.internal.kernel.api.exceptions.KernelException;

class ConcurrencyValidationTest extends BaseProcTest {

    @BeforeEach
    void setupGraph() throws KernelException {
        // we start a non-EE database
        db = TestDatabaseCreator.createTestDatabase();

        registerProcedures(PageRankStreamProc.class, PageRankWriteProc.class, GraphCreateProc.class);
        runQuery("CREATE (:A)");
        String createGraph = "CALL gds.graph.create('myG', '*', '*')";
        runQuery(createGraph);
    }

    @AfterEach
    void tearDown() {
        GraphCatalog.removeAllLoadedGraphs();
        db.shutdown();
    }

    @Test
    void shouldThrowOnTooHighConcurrency() {
        String query = "CALL gds.pageRank.stream('myG', {concurrency: 10})";

        assertError(
            query,
            "The configured concurrency value is too high. " +
            "The maximum allowed concurrency value is 4 but 10 was configured."
        );
    }

    @Test
    void shouldThrowOnTooHighReadConcurrency() {
        String query = "CALL gds.graph.create('myG2', '*', '*', {readConcurrency: 9})";

        assertError(
            query,
            "The configured concurrency value is too high. " +
            "The maximum allowed concurrency value is 4 but 9 was configured."
        );
    }

    @Test
    void shouldThrowOnTooHighWriteConcurrency() {
        String query = "CALL gds.pageRank.write('myG', {writeConcurrency: 12, writeProperty: 'p'})";

        assertError(
            query,
            "The configured concurrency value is too high. " +
            "The maximum allowed concurrency value is 4 but 12 was configured."
        );
    }
}
