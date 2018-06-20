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
package org.jumpmind.metl.ui.views.design.menu;

import org.jumpmind.metl.core.model.FlowName;
import org.jumpmind.metl.core.model.FolderName;
import org.jumpmind.metl.core.model.ModelName;
import org.jumpmind.metl.core.model.Project;
import org.jumpmind.metl.core.model.ProjectVersion;
import org.jumpmind.metl.core.model.ProjectVersionDepends;
import org.jumpmind.metl.core.model.ResourceName;
import org.jumpmind.metl.ui.common.AbstractMenuBar;
import org.jumpmind.metl.ui.views.design.DesignNavigator;

import com.vaadin.ui.AbstractSelect;

public class DesignMenuBar extends AbstractMenuBar {

    private static final long serialVersionUID = 1L;

    public DesignMenuBar(DesignNavigator navigator, AbstractSelect tree) {
        super(tree, new NothingSelectAction(navigator));
        addMenuManager(ProjectVersion.class, new ProjectVersionMenuManager(navigator));
        addMenuManager(Project.class, new ProjectMenuManager(navigator));
        addMenuManager(FlowName.class, new FlowMenuManager(navigator));
        addMenuManager(ResourceName.class, new ResourceMenuManager(navigator));
        addMenuManager(ModelName.class, new ModelMenuManager(navigator));
        addMenuManager(ProjectVersionDepends.class, new ProjectDependencyMenuManager(navigator));
        addMenuManager(FolderName.class, new FolderMenuManager(navigator));
    }

    @Override
    protected void buildMenu() {
        add("文件|新建|项目");
        add("文件|新建|项目分支");
        add("文件|新建|项目依赖");
        add("文件|新建|流程|设计");
        add("文件|新建|流程|测试");
        add("文件|新建|模型|层级");
        add("文件|新建|模型|关系");
        add("文件|新建|资源|数据库");
        add("文件|新建|资源|目录|FTP");
        add("文件|新建|资源|目录|文件系统");
        add("文件|新建|资源|目录|JMS");
        add("文件|新建|资源|目录|SFTP");
        add("文件|新建|资源|目录|SMB");
        add("文件|新建|资源|HTTP");
        add("文件|新建|资源|邮件会话");
        add("文件|新建|资源|订阅|JMS");
        add("文件|打开");
        addSeparator("文件");
        add("文件|导入...");
        add("文件|导出...");
        
        add("编辑|重命名");
        add("编辑|剪贴");
        add("编辑|复制");
        add("编辑|粘贴");
        addSeparator("编辑");
        add("编辑|更改依赖版本");
        addSeparator("编辑");
        add("编辑|删除");
        
        add("标签");
    }

    static class NothingSelectAction extends AbstractDesignSelectedValueMenuManager {

        public NothingSelectAction(DesignNavigator navigator) {
            super(navigator);
        }
        
        @Override
        protected boolean isReadOnly(Object selected) {
            return false;
        }
        
    }

}
