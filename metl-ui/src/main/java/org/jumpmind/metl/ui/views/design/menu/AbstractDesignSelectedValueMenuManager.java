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

import org.jumpmind.metl.core.model.AbstractNamedObject;
import org.jumpmind.metl.core.model.Model;
import org.jumpmind.metl.core.model.ProjectVersion;
import org.jumpmind.metl.ui.common.AbstractSelectedValueMenuManager;
import org.jumpmind.metl.ui.views.design.DesignNavigator;

abstract public class AbstractDesignSelectedValueMenuManager extends AbstractSelectedValueMenuManager {

    protected DesignNavigator navigator;

    public AbstractDesignSelectedValueMenuManager(DesignNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public boolean handle(String menuSelected, Object valueSelected) {
        boolean handled = false;
        if (!super.handle(menuSelected, valueSelected)) {
            if ("文件|新建|项目".equals(menuSelected)) {
                navigator.addNewProject();
                return true;
            } else if ("编辑|重命名".equals(menuSelected)) {
                navigator.startEditingItem((AbstractNamedObject) valueSelected);
                return true;
            } else if ("文件|新建|项目依赖".equals(menuSelected)) {
                navigator.promptForNewDependency();
                return true;
            } else if ("文件|新建|项目分支".equals(menuSelected)) {
                navigator.doNewProjectBranch();
                return true;
            } else if ("文件|新建|流程|设计".equals(menuSelected)) {
                navigator.addNewFlow(false);
                return true;
            } else if ("文件|新建|流程|测试".equals(menuSelected)) {
                navigator.addNewFlow(true);
                return true;
            } else if ("文件|新建|模型|层级".equals(menuSelected)) {
                navigator.addNewModel(Model.TYPE_HIERARCHICAL);
                return true;
            } else if ("文件|新建|模型|关系".equals(menuSelected)) {
                navigator.addNewModel(Model.TYPE_RELATIONAL);
                return true;
            } else if ("文件|新建|资源|数据库".equals(menuSelected)) {
                navigator.addNewDatabase();
                return true;
            } else if ("文件|新建|资源|目录|FTP".equals(menuSelected)) {
                navigator.addNewFtpFileSystem();
                return true;
            } else if ("文件|新建|资源|订阅|JMS".equals(menuSelected)) {
                navigator.addNewJmsSubscribe();
                return true;
            } else if ("文件|新建|资源|目录|文件系统".equals(menuSelected)) {
                navigator.addNewLocalFileSystem();
                return true;
            } else if ("文件|新建|资源|目录|JMS".equals(menuSelected)) {
                navigator.addNewJMSFileSystem();
                return true;
            } else if ("文件|新建|资源|目录|SFTP".equals(menuSelected)) {
                navigator.addNewSftpFileSystem();
                return true;
            } else if ("文件|新建|资源|目录|SMB".equals(menuSelected)) {
                navigator.addNewSMBFileSystem();
                return true;
            } else if ("文件|新建|资源|HTTP".equals(menuSelected)) {
                navigator.addNewHttpResource();
                return true;
            } else if ("文件|新建|资源|邮件会话".equals(menuSelected)) {
                navigator.addNewMailSession();
                return true;
            } else if ("文件|导入...".equals(menuSelected)) {
                navigator.doImport();
                return true;
            } else if ("文件|导出...".equals(menuSelected)) {
                navigator.doExport();
                return true;
            } else if ("文件|打开".equals(menuSelected)) {
                navigator.doOpen();
                return true;
            } else if ("编辑|删除".equals(menuSelected)) {
                navigator.doRemove();
                return true;
            } else if ("编辑|剪贴".equals(menuSelected)) {
                navigator.doCut();
                return true;
            } else if ("编辑|复制".equals(menuSelected)) {
                navigator.doCopy();
                return true;
            } else if ("编辑|粘贴".equals(menuSelected)) {
                navigator.doPaste();
            } else if ("编辑|更改依赖版本".equals(menuSelected)) {
                navigator.doChangeDependencyVersion();
            } else if ("标签".equals(menuSelected)) {
                navigator.doTag();
            }
        }
        return handled;
    }

    protected boolean isReadOnly(Object selected) {
        ProjectVersion projectVersion = navigator.findProjectVersion(selected);
        if (projectVersion != null) {
            return projectVersion.locked();
        } else {
            return false;
        }
    }

    protected String[] getDisabledPaths(Object selected) {
        if (isReadOnly(selected)) {
            return new String[] { "文件|新建|项目依赖", "文件|新建|流程|设计", "文件|新建|流程|测试",
                    "文件|新建|模型|层级", "文件|新建|模型|关系",
                    "文件|新建|资源|数据库", "文件|新建|资源|目录|FTP", "文件|新建|资源|目录|文件系统",
                    "文件|新建|资源|目录|JMS", "文件|新建|资源|目录|SFTP", "文件|新建|资源|目录|SMB",
                    "文件|新建|资源|HTTP", "文件|新建|资源|邮件会话", "文件|新建|资源|订阅|JMS", "编辑|重命名", "标签" };
        } else {
            return null;
        }
    }

    @Override
    protected String[] getEnabledPaths(Object selected) {
        return new String[] { "文件|新建|项目", "视图|隐藏", "文件|导入..." };
    }

}
