/**
 * Licensed to JumpMind Inc under one or more contributor
 * license agreements.  See the NOTICE file distributed
 * with this work for additional information regarding
 * copyright ownership.  JumpMind Inc licenses this file
 * to you under the GNU General Public License, version 3.0 (GPLv3)
 * (the "License"); you may not use this file except in compliance
 * with the License.
 *
 * You should have received a copy of the GNU General Public License,
 * version 3.0 (GPLv3) along with this library; if not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jumpmind.metl.core.model;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.List;

import org.jumpmind.metl.core.runtime.LogLevel;

public class AgentDeployment extends AbstractNamedObject {

    private static final long serialVersionUID = 1L;

    String name;

    Flow flow;

    String agentId;

    String status = DeploymentStatus.DISABLED.name();

    String message;
    
    String logLevel = LogLevel.INFO.name();

    String startType = StartType.MANUAL.name();

    String startExpression;
    
    List<AgentDeploymentParameter> agentDeploymentParameters;
    
    ProjectVersion projectVersion;

    public AgentDeployment() {
        agentDeploymentParameters = new ArrayList<AgentDeploymentParameter>();
    }

    public AgentDeployment(Flow flow) {
        this();
        this.name = flow.getName();
        setFlow(flow);
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getFlowId() {
        return flow != null ? flow.getId() : null;
    }

    public void setFlowId(String flowId) {
        if (flowId != null) {
            this.flow = new Flow();
            this.flow.setId(flowId);
        } else {
            this.flow = null;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = DeploymentStatus.massage(status);
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public DeploymentStatus getDeploymentStatus() {
        return DeploymentStatus.valueOf(status);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setStartExpression(String startExpression) {
        this.startExpression = startExpression;
    }

    public String getStartExpression() {
        return startExpression;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public String getStartType() {
        return startType;
    }

    public StartType asStartType() {
        if (isBlank(startType)) {
            return StartType.MANUAL;
        } else {
            return StartType.valueOf(startType);
        }
    }
    
    public LogLevel asLogLevel() {
        if (isBlank(logLevel)) {
            return LogLevel.DEBUG;
        } else {
            return LogLevel.valueOf(logLevel);
        }
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
    
    public void setAgentDeploymentParameters(
            List<AgentDeploymentParameter> agentDeploymentParameters) {
        this.agentDeploymentParameters = agentDeploymentParameters;
    }
    
    public List<AgentDeploymentParameter> getAgentDeploymentParameters() {
        return agentDeploymentParameters;
    }
    
    public void setProjectVersion(ProjectVersion projectVersion) {
        this.projectVersion = projectVersion;
    }
    
    public ProjectVersion getProjectVersion() {
        return projectVersion;
    }

    @Override
    public String toString() {
        return flow.getName();
    }
}
