# window

[![license](https://img.shields.io/github/license/GoldenStack/window?style=for-the-badge&color=dd2233)](../LICENSE)
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=for-the-badge)](https://github.com/RichardLitt/standard-readme)
[![javadocs](https://img.shields.io/badge/documentation-javadocs-4d7a97?style=for-the-badge)](https://javadoc.jitpack.io/com/github/GoldenStack/window/master-SNAPSHOT/javadoc/)

Useful API for Minestom inventories

When programming, Minecraft inventories are interfaced with using slot IDs. However, these IDs are completely arbitrary,
and don't hold much meaning; this leads to a lot of confusion when dealing with complicated inventories, like brewing
stands and player inventories. However, simple inventories and their subsections can still be meaningfully represented
as just slots IDs, like chest inventories, as they aren't complicated at all.

This library allows the user to represent each inventory as a tree of separate inventories, each with their own isolated
slot IDs, making it much easier to manage: for example, if an operation is applied to a subsection of an inventory, it
will only be applied to that subsection.

--- 

Essentially, this library manages a tree of inventory views, each knowing nothing about its position in the tree except
for a singular parent.

Additionally, all types defined here are fully immutable, and are not inherently tied to any specific inventory
(although you may do so if you like), so it shouldn't be overly complicated.

No matter how complicated the actual tree structure is, all views can:
- return their size
- be forked into subviews
- support arbitrary gets/sets
- collect themselves into a List<ItemStack>
- and more (check the documentation)

## Table of Contents
- [Install](#install)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Install

To install, simply add the library via [JitPack](https://jitpack.io/#GoldenStack/window/-SNAPSHOT):

Details for how to add this library with other build tools (such as Maven) can be found on the page linked above.
``` kts
repositories {
    // ...

    maven("https://jitpack.io")
}

dependencies {
    // ...
    implementation("com.github.GoldenStack:window:VERSION-HERE")
}
```

## Usage

You can use raw views via the static functions in `InventoryView` (try checking the documentation).

All of the code in the `Views` file just uses that internally, and the documentation should be extensive enough for you
to get what it means without an excessive amount of example code.

Here's some example code for using `Views` and some methods of `InventoryView`:

(the following snippets presume that there is a variable `inv` with the type `PlayerInventory`):

Different forms of modifying an inventory:
``` java
// Fill crafting input with wool
var input = Views.player().crafting().input();
input.fill(inv, slot -> ItemStack.of(Material.RED_WOOL, slot + 1));

// Clear only the input
input.clear(inv);

// Alternative form
for (int slot = 0; slot < input.size(); slot++) {
    input.set(inv, slot, ItemStack.of(Material.RED_WOOL, slot + 1));
}

// You can use views that aren't leafs in the tree, like the entire crafting menu (both the input and the result)
var entireCrafting = Views.player().crafting();
entireCrafting.fill(inv, slot -> ItemStack.of(Material.GRAY_CONCRETE, slot + 1));
```

Various ways to use the API to manage the armor of a player:
``` java
// Add armor
// Uses the interface InventoryView.Singular that allows you to omit the slot ID when getting and setting items
var armor = Views.player().armor();
armor.helmet().set(inv, ItemStack.of(Material.DIAMOND_HELMET));
armor.chestplate().set(inv, ItemStack.of(Material.DIAMOND_CHESTPLATE));
armor.leggings().set(inv, ItemStack.of(Material.DIAMOND_LEGGINGS));
armor.boots().set(inv, ItemStack.of(Material.DIAMOND_BOOTS));

// Print out slots
System.out.println(armor.helmet().get(inv));
System.out.println(armor.chestplate().get(inv));
System.out.println(armor.leggings().get(inv));
System.out.println(armor.boots().get(inv));

// Alternative:
for (int i = 0; i < armor.size(); i++) {
    System.out.println(armor.get(inv, i));
}

// Alternative that uses raw slot IDs
for (int i = 0; i < armor.size(); i++) {
    var rawSlot = armor.localToExternal(i);
    System.out.println(inv.getItemStack(rawSlot));
}
```

Various types of forking and joining views:
``` java
// Create a contiguous section
var view1 = InventoryView.contiguous(4, 22);

// Create a child of the contiguous section
var view2 = view1.forkRange(3, 11); // Maps to the slots in `view1`

// Create a child of the view with only one slot
var view3 = view2.fork(2); // Maps to the third slot in view2
view3.set(inv, ItemStack.of(Material.STONE));
// At this point, view3 doesn't know anything about whether or not it has a parent

// Dynamically link two inventories
var viewA = InventoryView.contiguous(0, 20);
var viewB = InventoryView.contiguous(3, 7);

var viewC = InventoryView.join(viewA, viewB);
// ViewC acts as an intermediate between viewA and viewB, providing an API equivalent to
// viewB but additionally treating viewA as a parent. This intermediate doesn't know anything
// about the specific implementation of either, so this method should work with any custom implementation.
// Thus, the viewC.set(inv, 0) is equivalent to viewA.set(inv, 3)

// Create an arbitrary union, allowing complex inventory sectioning
var arbitraryUnion = InventoryView.union(
        InventoryView.contiguous(0, 1),
        InventoryView.contiguous(4, 8),
        InventoryView.contiguous(11, 22),
        InventoryView.contiguous(24, 33)
);

arbitraryUnion.fill(inv, slot -> ItemStack.of(Material.RED_WOOL, slot + 1));

// Create an arbitrary fork, the most versatile type:
// You can specify the exact slot IDs and it'll map to them in the order specified
var arbitraryFork = Views.player().contents().fork(1, 3, 7, 2, 21);
arbitraryFork.size(); // Returns 5
arbitraryFork.localToExternal(2); // returns 7
```

## Contributing

Found a bug? Explain it clearly in a new issue.

Fixed a bug? Feel free to open a pull request.

Adding a feature? Make sure to check with a maintainer that it's actually wanted.


All contributions made and submitted are licensed under [MIT](../LICENSE).

## License

All code in this project is licensed under [MIT](../LICENSE)