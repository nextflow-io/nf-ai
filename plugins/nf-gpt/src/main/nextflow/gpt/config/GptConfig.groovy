/*
 * Copyright 2013-2024, Seqera Labs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package nextflow.gpt.config

import groovy.transform.CompileStatic
import groovy.transform.ToString
import nextflow.Global
import nextflow.Session
import nextflow.SysEnv
/**
 * Model AI configuration
 * 
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
@ToString(includeFields = true, includeNames = true, includePackage = false)
@CompileStatic
class GptConfig {

    static final String DEFAULT_ENDPOINT = 'https://api.openai.com'
    static final String DEFAULT_MODEL = 'gpt-3.5-turbo'
    static final Double DEFAULT_TEMPERATURE = 0.7d

    private String endpoint
    private String apiKey
    private String model
    private Double temperature
    private Integer maxTokens
    private GptRetryOpts retryOpts

    static GptConfig config(Session session) {
        new GptConfig(session.config.gpt as Map ?: Collections.emptyMap(), SysEnv.get())
    }

    static GptConfig config() {
        config(Global.session as Session)
    }

    GptConfig(Map opts, Map<String,String> env) {
        this.endpoint = opts.endpoint ?: DEFAULT_ENDPOINT
        this.model = opts.model ?: DEFAULT_MODEL
        this.apiKey = opts.apiKey ?: env.get('OPENAI_API_KEY')
        this.temperature = opts.temperature!=null ? temperature as Double : DEFAULT_TEMPERATURE
        this.retryOpts = new GptRetryOpts( opts.retryPolicy as Map ?: Map.of() )
    }

    String endpoint() {
        return endpoint
    }

    String apiKey() {
        return apiKey
    }

    String model() {
        return model
    }

    Double temperature() {
        return temperature
    }

    Integer maxTokens() {
        return maxTokens
    }

    GptRetryOpts retryOpts() {
        return retryOpts
    }
}
