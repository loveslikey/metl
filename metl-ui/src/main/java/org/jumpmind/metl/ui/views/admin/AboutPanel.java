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
package org.jumpmind.metl.ui.views.admin;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.time.FastDateFormat;
import org.jumpmind.metl.core.runtime.AgentManager;
import org.jumpmind.metl.core.util.VersionUtils;
import org.jumpmind.metl.ui.common.IBackgroundRefreshable;
import org.jumpmind.metl.ui.common.Table;
import org.jumpmind.util.AppUtils;
import org.jumpmind.vaadin.ui.common.CommonUiUtils;
import org.jumpmind.vaadin.ui.common.UiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

@UiComponent
@Scope(value = "ui")
@Order(1400)
@AdminMenuLink(name = "About", id = "About", icon = FontAwesome.QUESTION)
public class AboutPanel extends AbstractAdminPanel implements IBackgroundRefreshable<Object> {

    final Logger log = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;

    Table table;

    public AboutPanel() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        Button gcCollect = new Button("垃圾收集", (e) -> {
            Runtime.getRuntime().gc();
            refresh();
        });
        gcCollect.addStyleName(ValoTheme.BUTTON_TINY);
        addComponent(gcCollect);

        table = new Table();
        table.setSizeFull();
        table.addStyleName("noscroll");
        table.addContainerProperty("Name", String.class, null);
        table.setColumnWidth("Name", 200);
        table.addContainerProperty("Value", String.class, null);
        addComponent(table);
        setExpandRatio(table, 1);
    }
    
    @PostConstruct
    @Override
    public void init() {
        super.init();
        context.getBackgroundRefresherService().register(this);
    }
    

    @Override
    public boolean closing() {
        context.getBackgroundRefresherService().unregister(this);
        return true;
    }

    @Override
    public void deselected() {
    }

    @Override
    public void selected() {
        onBackgroundDataRefresh();
    }

    @Override
    public Object onBackgroundDataRefresh() {
        return new Object();
    }

    @Override
    public void onBackgroundUIRefresh(Object backgroundData) {
        refresh();
    }

    @Override
    public void onUIError(Throwable ex) {
        CommonUiUtils.notify(ex);        
    }

    protected void refresh() {
        table.removeAllItems();
        int itemId = 0;
        table.addItem(new Object[] { "应用程序版本", VersionUtils.getCurrentVersion() },
                itemId++);
        table.addItem(new Object[] { "建立时间", VersionUtils.getBuildTime() }, itemId++);
        table.addItem(new Object[] { "SCM修订", VersionUtils.getScmVersion() }, itemId++);
        table.addItem(new Object[] { "SCM分支", VersionUtils.getScmBranch() }, itemId++);

        table.addItem(new Object[] { "主机名", AppUtils.getHostName() }, itemId++);
        table.addItem(new Object[] { "IP地址", AppUtils.getIpAddress() }, itemId++);
        table.addItem(new Object[] { "Java版本", System.getProperty("java.version") },
                itemId++);
        table.addItem(
                new Object[] { "系统时间",
                        FastDateFormat.getTimeInstance(FastDateFormat.MEDIUM).format(new Date()) },
                itemId++);
        table.addItem(
                new Object[] { "使用的堆内存", Long.toString(
                        Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) },
                itemId++);
        table.addItem(new Object[] { "堆内存大小", Long.toString(Runtime.getRuntime().maxMemory()) },
                itemId++);
        table.addItem(new Object[] { "最后重启时间",
                CommonUiUtils.formatDateTime(AgentManager.lastRestartTime) }, itemId++);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
