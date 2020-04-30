/*
 * Copyright 2020 the original author or authors.
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

package org.gradle.instantexecution.inputs.undeclared

class UndeclaredBuildInputsDynamicGroovyScriptPluginIntegrationTest extends AbstractUndeclaredBuildInputsIntegrationTest implements GroovyPluginImplementation {
    @Override
    void buildLogicApplication() {
        def script = file("plugin.gradle")
        dynamicGroovyPlugin(script)

        script << """
            apply plugin: SneakyPlugin
            println("apply SCRIPT = " + System.getProperty("SCRIPT"))
        """

        buildFile << """
            apply from: "plugin.gradle"
        """
    }

    @Override
    void additionalProblems() {
        outputContains("- unknown location: read system property 'SCRIPT' from '")
    }
}