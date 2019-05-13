/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.initialization;

import org.gradle.api.internal.GradleInternal;
import org.gradle.composite.internal.IncludedBuildControllers;
import org.gradle.execution.BuildConfigurationActionExecuter;
import org.gradle.execution.taskgraph.TaskExecutionGraphInternal;

public class DefaultTaskExecutionPreparer implements TaskExecutionPreparer {
    private final BuildConfigurationActionExecuter buildConfigurationActionExecuter;
    private final IncludedBuildControllers includedBuildControllers;

    public DefaultTaskExecutionPreparer(BuildConfigurationActionExecuter buildConfigurationActionExecuter, IncludedBuildControllers includedBuildControllers) {
        this.buildConfigurationActionExecuter = buildConfigurationActionExecuter;
        this.includedBuildControllers = includedBuildControllers;
    }

    @Override
    public void prepareForTaskExecution(GradleInternal gradle) {
        buildConfigurationActionExecuter.select(gradle);

        TaskExecutionGraphInternal taskGraph = gradle.getTaskGraph();
        taskGraph.populate();

        includedBuildControllers.populateTaskGraphs();
    }
}
