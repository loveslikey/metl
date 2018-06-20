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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jumpmind.metl.core.model.Group;
import org.jumpmind.metl.core.model.User;
import org.jumpmind.metl.ui.common.ApplicationContext;
import org.jumpmind.metl.ui.common.ButtonBar;
import org.jumpmind.metl.ui.common.TabbedPanel;
import org.jumpmind.vaadin.ui.common.IUiPanel;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class GroupPanel extends VerticalLayout implements IUiPanel {

    ApplicationContext context;

    TabbedPanel tabbedPanel;

    Button newButton;
    
    Button editButton;
    
    Button removeButton;

    BeanItemContainer<Group> container;
    
    Table table;

    public GroupPanel(ApplicationContext context, TabbedPanel tabbedPanel) {
        this.context = context;
        this.tabbedPanel = tabbedPanel;

        ButtonBar buttonBar = new ButtonBar();
        addComponent(buttonBar);

        newButton = buttonBar.addButton("新增", FontAwesome.PLUS);
        newButton.addClickListener(new NewClickListener());

        editButton = buttonBar.addButton("编辑", FontAwesome.EDIT);
        editButton.addClickListener(new EditClickListener());

        removeButton = buttonBar.addButton("删除", FontAwesome.TRASH_O);
        removeButton.addClickListener(new RemoveClickListener());

        container = new BeanItemContainer<Group>(Group.class);

        table = new Table();
        table.setSizeFull();
        table.setCacheRate(100);
        table.setPageLength(100);
        table.setImmediate(true);
        table.setSelectable(true);
        table.setMultiSelect(true);

        table.setContainerDataSource(container);
        table.setVisibleColumns("name", "createTime", "lastUpdateTime");
        table.setColumnHeaders("名称", "创建时间", "更新时间");
        table.addItemClickListener(new TableItemClickListener());
        table.addValueChangeListener(new TableValueChangeListener());
        table.setSortContainerPropertyId("name");
        table.setSortAscending(true);

        addComponent(table);
        setExpandRatio(table, 1.0f);
        refresh();
    }

    @Override
    public void selected() {
        refresh();
    }

    @Override
    public boolean closing() {
        return true;
    }

    @Override
    public void deselected() {
    }

    public void refresh() {
        container.removeAllItems();
        container.addAll(context.getOperationsService().findGroups());
        table.sort();
        setButtonsEnabled();
    }

    protected void setButtonsEnabled() {
        Set<Group> selectedIds = getSelectedItems();
        boolean enabled = selectedIds.size() > 0;
        editButton.setEnabled(enabled);
        removeButton.setEnabled(enabled);
    }

    @SuppressWarnings("unchecked")
    protected Set<Group> getSelectedItems() {
        return (Set<Group>) table.getValue();
    }

    @SuppressWarnings("unchecked")
    protected Group getFirstSelectedItem() {
        Set<Group> groups = (Set<Group>) table.getValue();
        Iterator<Group> iter = groups.iterator();
        if (iter.hasNext()) {
            return iter.next();
        }
        return null;
    }

    class NewClickListener implements ClickListener {
        public void buttonClick(ClickEvent event) {
            Group group = new Group();
            GroupEditPanel editPanel = new GroupEditPanel(context, group);
            tabbedPanel.addCloseableTab(group.getId(), "新增用户组", getIcon(), editPanel);
        }
    }

    class EditClickListener implements ClickListener {
        public void buttonClick(ClickEvent event) {
            Group group = getFirstSelectedItem();
            context.getOperationsService().refresh(group);
            GroupEditPanel editPanel = new GroupEditPanel(context, group);
            tabbedPanel.addCloseableTab(group.getId(), "编辑用户组", getIcon(), editPanel);
        }
    }

    class RemoveClickListener implements ClickListener {
        public void buttonClick(ClickEvent event) {
            List<User> users = new ArrayList<User>();
            for (Group group : getSelectedItems()) {
                users.addAll(context.getOperationsService().findUsersByGroup(group.getId()));
                if (users.size() > 10) {
                    break;
                }
            }
            if (users.size() == 0) {
                for (Group group : getSelectedItems()) {
                    context.getConfigurationService().delete(group);
                    container.removeItem(group);
                }
            } else {
                String message = "已有 " + users.size() + " 个用户分配到该用户组。" +
                        "请先重新分配或删除这些用户。";
                if (users.size() < 10) {
                    message += users.toString();
                }
                Notification note = new Notification("不能删除", message);
                note.show(Page.getCurrent());
            }
            table.setValue(null);
            setButtonsEnabled();
        }
    }

    class TableItemClickListener implements ItemClickListener {
        long lastClick;
        
        public void itemClick(ItemClickEvent event) {
            if (event.isDoubleClick()) {
                editButton.click();
            } else if (getSelectedItems().contains(event.getItemId()) &&
                System.currentTimeMillis()-lastClick > 500) {
                    table.setValue(null);
            }
            lastClick = System.currentTimeMillis();
        }
    }

    class TableValueChangeListener implements ValueChangeListener {
        public void valueChange(ValueChangeEvent event) {
            setButtonsEnabled();
        }
    }
}
