package io.github.vampirestudios.raa.filters;

import net.minecraft.block.Block;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class FilterEntry {

    private final Identifier tag;
    private final String translationKey;
    private final ItemStack icon;
    private boolean enabled = true;
    private final List<Item> items = new ArrayList<>();

    public FilterEntry(Identifier tag, ItemStack icon) {
        this.tag = tag;
        this.translationKey = String.format("gui.tag_filter.%s.%s", tag.getNamespace(), tag.getPath().replace("/", "."));
        this.icon = icon;
    }

    public Identifier getTag()
    {
        return tag;
    }

    public ItemStack getIcon()
    {
        return this.icon;
    }

    public String getName()
    {
        return I18n.translate(this.translationKey);
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    void add(Item item)
    {
        this.items.add(item);
    }

    void add(Block block)
    {
        this.items.add(block.asItem());
    }

    void clear()
    {
        this.items.clear();
    }

    public List<Item> getItems()
    {
        return this.items;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        FilterEntry that = (FilterEntry) o;
        return this.tag.equals(that.tag);
    }

    @Override
    public int hashCode()
    {
        return this.tag.hashCode();
    }
}