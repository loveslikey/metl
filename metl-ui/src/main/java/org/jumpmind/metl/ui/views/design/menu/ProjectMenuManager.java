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
import org.jumpmind.metl.ui.views.design.DesignNavigator;

public class ProjectMenuManager extends AbstractDesignSelectedValueMenuManager {

    public ProjectMenuManager(DesignNavigator navigator) {
        super(navigator);
    }
    
    @Override
    protected String[] getEnabledPaths(Object selected) {
        return (String[])ArrayUtils.addAll(super.getEnabledPaths(selected), new String[] {
                "文件|隐藏",
                "编辑|重命名",
                "编辑|删除",
                "标签",
        });
    }

}
