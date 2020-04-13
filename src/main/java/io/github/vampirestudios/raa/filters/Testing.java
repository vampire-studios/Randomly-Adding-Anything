package io.github.vampirestudios.raa.filters;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.stream.Collectors;

public class Testing {

    private static final Identifier ICONS = new Identifier(RandomlyAddingAnything.MOD_ID, "textures/gui/icons.png");
    private static Map<ItemGroup, Integer> scrollMap = new HashMap<>();

    private boolean updatedFilters;
    private List<TagButton> buttons = new ArrayList<>();
    private Map<ItemGroup, FilterEntry> miscFilterMap = new HashMap<>();
    private ButtonWidget btnScrollUp;
    private ButtonWidget btnScrollDown;
    private ButtonWidget btnEnableAll;
    private ButtonWidget btnDisableAll;
    private boolean viewingFilterTab;
    private int guiCenterX = 0;
    private int guiCenterY = 0;
    private ItemGroup currentGroup = ItemGroup.BUILDING_BLOCKS;

    public void onInitialize(Screen screen2) {
        if(!this.updatedFilters)
        {
            this.updateFilters();
            this.updatedFilters = true;
        }

        this.viewingFilterTab = false;
        this.guiCenterX = ((CreativeInventoryScreen) screen2).width;
        this.guiCenterY = ((CreativeInventoryScreen) screen2).height;

        screen2.addChild(this.btnScrollUp = new IconButton(this.guiCenterX - 22, this.guiCenterY - 12, I18n.translate("gui.button.filters.scroll_filters_up"), button -> this.scrollUp(), ICONS, 0, 0));
        screen2.addChild(this.btnScrollDown = new IconButton(this.guiCenterX - 22, this.guiCenterY + 127, I18n.translate("gui.button.filters.scroll_filters_down"), button -> this.scrollDown(), ICONS, 16, 0));
        screen2.addChild(this.btnEnableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 10, I18n.translate("gui.button.filters.enable_filters"), button -> this.enableAllFilters(), ICONS, 32, 0));
        screen2.addChild(this.btnDisableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 32, I18n.translate("gui.button.filters.disable_filters"), button -> this.disableAllFilters(), ICONS, 48, 0));

        this.hideButtons();

        CreativeInventoryScreen screen = (CreativeInventoryScreen) screen2;
        this.updateTagButtons(screen);

        ItemGroup group = this.getGroup(screen.getSelectedTab());
        if(Filters.get().hasFilters(group))
        {
            this.showButtons();
            this.viewingFilterTab = true;
            this.updateItems(screen);
        }

        this.currentGroup = group;
    }



    private void updateTagButtons(CreativeInventoryScreen screen)
    {
        if(!this.updatedFilters)
            return;

        this.buttons.clear();
        ItemGroup group = this.getGroup(screen.getSelectedTab());
        if(Filters.get().hasFilters(group))
        {
            List<FilterEntry> entries = this.getFilters(group);
            int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
            for(int i = scroll; i < scroll + 4 && i < entries.size(); i++)
            {
                TagButton button = new TagButton(screen.width - 28, screen.height + 29 * (i - scroll) + 10, entries.get(i), button1 -> this.updateItems(screen));
                this.buttons.add(button);
            }
            this.btnScrollUp.active = scroll > 0;
            this.btnScrollDown.active = scroll <= entries.size() - 4 - 1;
            this.showButtons();
        }
        else
        {
            this.hideButtons();
        }
    }

    private void updateItems(CreativeInventoryScreen screen)
    {
        CreativeInventoryScreen.CreativeScreenHandler container = screen.getScreenHandler();
        Set<Item> filteredItems = new LinkedHashSet<>();
        ItemGroup group = this.getGroup(screen.getSelectedTab());
        if(group != null)
        {
            if(Filters.get().hasFilters(group))
            {
                List<FilterEntry> entries = Filters.get().getFilters(group);
                if(entries != null)
                {
                    for(FilterEntry filter : this.getFilters(group))
                    {
                        if(filter.isEnabled())
                        {
                            filteredItems.addAll(filter.getItems());
                        }
                    }
                    container.itemList.clear();
                    filteredItems.forEach(item -> item.appendStacks(group, container.itemList));
                    container.itemList.sort(Comparator.comparingInt(o -> Item.getRawId(o.getItem())));
                    container.scrollItems(0);
                }
            }
        }
    }

    private void updateFilters()
    {
        Filters.get().getGroups().forEach(group ->
        {
            List<FilterEntry> entries = Filters.get().getFilters(group);
            entries.forEach(FilterEntry::clear);

            Set<Item> removed = new HashSet<>();
            List<Item> items = Registry.ITEM.stream()
                    .filter(item -> item.getGroup() == group || item == Items.ENCHANTED_BOOK)
                    .collect(Collectors.toList());
            /*items.forEach(item ->
            {
                for(Identifier location : item.())
                {
                    for(FilterEntry filter : entries)
                    {
                        if(location.equals(filter.getTag()))
                        {
                            filter.add(item);
                            removed.add(item);
                        }
                    }
                }
            });*/
            items.removeAll(removed);

            if(group.getEnchantments().length == 0)
            {
                items.remove(Items.ENCHANTED_BOOK);
            }

            if(!items.isEmpty())
            {
                FilterEntry entry = new FilterEntry(new Identifier("miscellaneous"), new ItemStack(Blocks.BARRIER));
                items.forEach(entry::add);
                this.miscFilterMap.put(group, entry);
            }
        });
    }

    private ItemGroup getGroup(int index)
    {
        if(index < 0 || index >= ItemGroup.GROUPS.length)
            return null;
        return ItemGroup.GROUPS[index];
    }

    private List<FilterEntry> getFilters(ItemGroup group)
    {
        if(Filters.get().hasFilters(group))
        {
            List<FilterEntry> filters = new ArrayList<>(Filters.get().getFilters(group));
            if(this.miscFilterMap.containsKey(group))
            {
                filters.add(this.miscFilterMap.get(group));
            }
            return filters;
        }
        return Collections.emptyList();
    }

    private void showButtons()
    {
        this.btnScrollUp.visible = true;
        this.btnScrollDown.visible = true;
        this.btnEnableAll.visible = true;
        this.btnDisableAll.visible = true;
        this.buttons.forEach(button -> button.visible = true);
    }

    private void hideButtons()
    {
        this.btnScrollUp.visible = false;
        this.btnScrollDown.visible = false;
        this.btnEnableAll.visible = false;
        this.btnDisableAll.visible = false;
        this.buttons.forEach(button -> button.visible = false);
    }

    private void scrollUp()
    {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        if(screen instanceof CreativeInventoryScreen)
        {
            CreativeInventoryScreen creativeScreen = (CreativeInventoryScreen) screen;
            ItemGroup group = this.getGroup(creativeScreen.getSelectedTab());
            List<FilterEntry> entries = this.getFilters(group);
            if(entries != null)
            {
                int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
                if(scroll > 0)
                {
                    scrollMap.put(group, scroll - 1);
                    this.updateTagButtons(creativeScreen);
                }
            }
        }
    }

    private void scrollDown()
    {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        if(screen instanceof CreativeInventoryScreen)
        {
            CreativeInventoryScreen creativeScreen = (CreativeInventoryScreen) screen;
            ItemGroup group = this.getGroup(creativeScreen.getSelectedTab());
            List<FilterEntry> entries = this.getFilters(group);
            if(entries != null)
            {
                int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
                if(scroll <= entries.size() - 4 - 1)
                {
                    scrollMap.put(group, scroll + 1);
                    this.updateTagButtons(creativeScreen);
                }
            }
        }
    }

    private void enableAllFilters()
    {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        if(screen instanceof CreativeInventoryScreen)
        {
            CreativeInventoryScreen creativeScreen = (CreativeInventoryScreen) screen;
            ItemGroup group = this.getGroup(creativeScreen.getSelectedTab());
            List<FilterEntry> entries = this.getFilters(group);
            if(entries != null)
            {
                entries.forEach(entry -> entry.setEnabled(true));
                this.buttons.forEach(TagButton::updateState);
                this.updateItems(creativeScreen);
            }
        }
    }

    private void disableAllFilters()
    {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        if(screen instanceof CreativeInventoryScreen)
        {
            CreativeInventoryScreen creativeScreen = (CreativeInventoryScreen) screen;
            ItemGroup group = this.getGroup(creativeScreen.getSelectedTab());
            List<FilterEntry> entries = this.getFilters(group);
            if(entries != null)
            {
                entries.forEach(filters -> filters.setEnabled(false));
                this.buttons.forEach(TagButton::updateState);
                this.updateItems(creativeScreen);
            }
        }
    }

}
