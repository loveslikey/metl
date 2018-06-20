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

import org.apache.commons.lang.ArrayUtils;
import org.jumpmind.metl.ui.common.CutCopyPasteManager;
import org.jumpmind.metl.ui.views.design.DesignNavigator;

public class ResourceMenuManager extends AbstractDesignSelectedValueMenuManager {

    public ResourceMenuManager(DesignNavigator navigator) {
        super(navigator);
    }
    
    @Override
    public boolean handle(String menuSelected, Object selected) {
        if (!super.handle(menuSelected, selected)) {            
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    protected String[] getDisabledPaths(Object selected) {
        if (isReadOnly(selected)) {
            return (String[])ArrayUtils.addAll(super.getDisabledPaths(selected), new String[] { "编辑|删除"
            });            
        } else {
            return super.getDisabledPaths(selected);
        }
    }
    
    @Override
    protected String[] getEnabledPaths(Object selected) {
        String[] enabledPaths = (String[]) ArrayUtils.addAll(super.getEnabledPaths(selected), new String[] {
                "文件|新建|项目依赖",
                "文件|新建|流程|设计",
                "文件|新建|流程|测试",
                "文件|新建|模型|层级",
                "文件|新建|模型|关系",
                "文件|新建|资源|数据库",
                "文件|新建|资源|目录|FTP",
                "文件|新建|资源|目录|文件系统",
                "文件|新建|资源|目录|JMS",
                "文件|新建|资源|目录|SFTP",
                "文件|新建|资源|目录|SMB",
                "文件|新建|资源|HTTP",
                "文件|新建|资源|邮件会话",
                "文件|新建|资源|订阅|JMS",
                "文件|打开",
                "文件|导入...",
                "文件|导出...",
                "编辑|重命名",
                "编辑|剪贴",
                "编辑|复制",
                "编辑|删除"
        });
        if (navigator.getContext().getClipboard()
                .containsKey(CutCopyPasteManager.CLIPBOARD_OBJECT_TYPE)) {
            enabledPaths = (String[]) ArrayUtils.add(enabledPaths, "编辑|粘贴");
        }
        return enabledPaths;         
    }
}
