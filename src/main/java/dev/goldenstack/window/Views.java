package dev.goldenstack.window;

import org.jetbrains.annotations.NotNull;

/**
 * Stores commonly-used views of Minecraft inventories, for version 1.20.4.
 */
@SuppressWarnings("unused")
public interface Views {

    @NotNull Anvil ANVIL = new Anvil();

    static @NotNull Anvil anvil() {
        return Views.ANVIL;
    }

    /**
     * Provides a view into an anvil
     * @param view a contiguous section of slots 0 to 3
     * @param input {@link Anvil#VIEW} slot 0
     * @param modifier {@link Anvil#VIEW} slot 1
     * @param output {@link Anvil#VIEW} slot 2
     */
    record Anvil(@NotNull InventoryView view,
                 @NotNull InventoryView.Singular input,
                 @NotNull InventoryView.Singular modifier,
                 @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 3);
        public static final @NotNull InventoryView.Singular INPUT = Anvil.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular MODIFIER = Anvil.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular OUTPUT = Anvil.VIEW.fork(2);

        public Anvil() {
            this(VIEW, INPUT, MODIFIER, OUTPUT);
        }
    }

    @NotNull Barrel BARREL = new Barrel();

    static @NotNull Barrel barrel() {
        return Views.BARREL;
    }

    /**
     * Provides a view into a barrel
     * @param view a contiguous section of slots 0 to 27
     * @param storage {@link Barrel#VIEW} slots 0 to 27
     */
    record Barrel(@NotNull InventoryView view,
                  @NotNull InventoryView storage) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 27);
        public static final @NotNull InventoryView STORAGE = Barrel.VIEW.forkRange(0, 27);

        public Barrel() {
            this(VIEW, STORAGE);
        }
    }

    @NotNull Beacon BEACON = new Beacon();

    static @NotNull Beacon beacon() {
        return Views.BEACON;
    }

    /**
     * Provides a view into a beacon
     * @param view slot 0
     * @param sacrifice {@link Beacon#VIEW} slot 0
     */
    record Beacon(@NotNull InventoryView view,
                  @NotNull InventoryView.Singular sacrifice) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.singular(0);
        public static final @NotNull InventoryView.Singular SACRIFICE = Beacon.VIEW.fork(0);

        public Beacon() {
            this(VIEW, SACRIFICE);
        }
    }

    @NotNull BlastFurnace BLAST_FURNACE = new BlastFurnace();

    static @NotNull BlastFurnace blastFurnace() {
        return Views.BLAST_FURNACE;
    }

    /**
     * Provides a view into a blast furnace
     * @param view a contiguous section of slots 0 to 3
     * @param input {@link BlastFurnace#VIEW} slot 0
     * @param fuel {@link BlastFurnace#VIEW} slot 1
     * @param output {@link BlastFurnace#VIEW} slot 2
     */
    record BlastFurnace(@NotNull InventoryView view,
                        @NotNull InventoryView.Singular input,
                        @NotNull InventoryView.Singular fuel,
                        @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 3);
        public static final @NotNull InventoryView.Singular INPUT = BlastFurnace.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular FUEL = BlastFurnace.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular OUTPUT = BlastFurnace.VIEW.fork(2);

        public BlastFurnace() {
            this(VIEW, INPUT, FUEL, OUTPUT);
        }
    }

    @NotNull BrewingStand BREWING_STAND = new BrewingStand();

    static @NotNull BrewingStand brewingStand() {
        return Views.BREWING_STAND;
    }

    /**
     * Provides a view into a brewing stand
     * @param view a contiguous section of slots 0 to 5
     * @param bottles {@link BrewingStand#VIEW} slots 0 to 3
     * @param ingredient {@link BrewingStand#VIEW} slot 3
     * @param fuel {@link BrewingStand#VIEW} slot 4
     */
    record BrewingStand(@NotNull InventoryView view,
                        @NotNull InventoryView bottles,
                        @NotNull InventoryView.Singular ingredient,
                        @NotNull InventoryView.Singular fuel) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 5);
        public static final @NotNull InventoryView BOTTLES = BrewingStand.VIEW.forkRange(0, 3);
        public static final @NotNull InventoryView.Singular INGREDIENT = BrewingStand.VIEW.fork(3);
        public static final @NotNull InventoryView.Singular FUEL = BrewingStand.VIEW.fork(4);

        public BrewingStand() {
            this(VIEW, BOTTLES, INGREDIENT, FUEL);
        }
    }

    @NotNull CartographyTable CARTOGRAPHY_TABLE = new CartographyTable();

    static @NotNull CartographyTable cartographyTable() {
        return Views.CARTOGRAPHY_TABLE;
    }

    /**
     * Provides a view into a cartography table
     * @param view a contiguous section of slots 0 to 3
     * @param input {@link CartographyTable#VIEW} slot 0
     * @param modifier {@link CartographyTable#VIEW} slot 1
     * @param output {@link CartographyTable#VIEW} slot 2
     */
    record CartographyTable(@NotNull InventoryView view,
                            @NotNull InventoryView.Singular input,
                            @NotNull InventoryView.Singular modifier,
                            @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 3);
        public static final @NotNull InventoryView.Singular INPUT = CartographyTable.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular MODIFIER = CartographyTable.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular OUTPUT = CartographyTable.VIEW.fork(2);

        public CartographyTable() {
            this(VIEW, INPUT, MODIFIER, OUTPUT);
        }
    }

    @NotNull Chest CHEST = new Chest();

    static @NotNull Chest chest() {
        return Views.CHEST;
    }

    /**
     * Provides a view into a single chest
     * @param view a contiguous section of slots 0 to 27
     * @param storage {@link Chest#VIEW} slots 0 to 27
     */
    record Chest(@NotNull InventoryView view,
                 @NotNull InventoryView storage) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 27);
        public static final @NotNull InventoryView STORAGE = Chest.VIEW.forkRange(0, 27);

        public Chest() {
            this(VIEW, STORAGE);
        }
    }

    @NotNull CraftingTable CRAFTING_TABLE = new CraftingTable();

    static @NotNull CraftingTable craftingTable() {
        return Views.CRAFTING_TABLE;
    }

    /**
     * Provides a view into a crafting table
     * @param view a contiguous section of slots 0 to 10
     * @param output {@link CraftingTable#VIEW} slot 0
     * @param input {@link CraftingTable#VIEW} slots 1 to 10
     */
    record CraftingTable(@NotNull InventoryView view,
                         @NotNull InventoryView.Singular output,
                         @NotNull InventoryView input) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 10);
        public static final @NotNull InventoryView.Singular OUTPUT = CraftingTable.VIEW.fork(0);
        public static final @NotNull InventoryView INPUT = CraftingTable.VIEW.forkRange(1, 10);

        public CraftingTable() {
            this(VIEW, OUTPUT, INPUT);
        }
    }

    @NotNull Dispenser DISPENSER = new Dispenser();

    static @NotNull Dispenser dispenser() {
        return Views.DISPENSER;
    }

    /**
     * Provides a view into a dispenser
     * @param view a contiguous section of slots 0 to 9
     * @param storage {@link Dispenser#VIEW} slots 0 to 9
     */
    record Dispenser(@NotNull InventoryView view,
                     @NotNull InventoryView storage) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 9);
        public static final @NotNull InventoryView STORAGE = Dispenser.VIEW.forkRange(0, 9);

        public Dispenser() {
            this(VIEW, STORAGE);
        }
    }

    @NotNull DoubleChest DOUBLE_CHEST = new DoubleChest();

    static @NotNull DoubleChest doubleChest() {
        return Views.DOUBLE_CHEST;
    }

    /**
     * Provides a view into a double chest
     * @param view a contiguous section of slots 0 to 54
     * @param storage {@link DoubleChest#VIEW} slots 0 to 54
     */
    record DoubleChest(@NotNull InventoryView view,
                       @NotNull InventoryView storage) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 54);
        public static final @NotNull InventoryView STORAGE = DoubleChest.VIEW.forkRange(0, 54);

        public DoubleChest() {
            this(VIEW, STORAGE);
        }
    }

    @NotNull Dropper DROPPER = new Dropper();

    static @NotNull Dropper dropper() {
        return Views.DROPPER;
    }

    /**
     * Provides a view into a dropper
     * @param view a contiguous section of slots 0 to 9
     * @param storage {@link Dropper#VIEW} slots 0 to 9
     */
    record Dropper(@NotNull InventoryView view,
                   @NotNull InventoryView storage) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 9);
        public static final @NotNull InventoryView STORAGE = Dropper.VIEW.forkRange(0, 9);

        public Dropper() {
            this(VIEW, STORAGE);
        }
    }

    @NotNull EnchantingTable ENCHANTING_TABLE = new EnchantingTable();

    static @NotNull EnchantingTable enchantingTable() {
        return Views.ENCHANTING_TABLE;
    }

    /**
     * Provides a view into an enchanting table
     * @param view a contiguous section of slots 0 to 2
     * @param item {@link EnchantingTable#VIEW} slot 0
     * @param fuel {@link EnchantingTable#VIEW} slot 1
     */
    record EnchantingTable(@NotNull InventoryView view,
                           @NotNull InventoryView.Singular item,
                           @NotNull InventoryView.Singular fuel) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 2);
        public static final @NotNull InventoryView.Singular ITEM = EnchantingTable.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular FUEL = EnchantingTable.VIEW.fork(1);

        public EnchantingTable() {
            this(VIEW, ITEM, FUEL);
        }
    }

    @NotNull EnderChest ENDER_CHEST = new EnderChest();

    static @NotNull EnderChest enderChest() {
        return Views.ENDER_CHEST;
    }

    /**
     * Provides a view into an ender chest
     * @param view a contiguous section of slots 0 to 27
     * @param storage {@link EnderChest#VIEW} slots 0 to 27
     */
    record EnderChest(@NotNull InventoryView view,
                      @NotNull InventoryView storage) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 27);
        public static final @NotNull InventoryView STORAGE = EnderChest.VIEW.forkRange(0, 27);

        public EnderChest() {
            this(VIEW, STORAGE);
        }
    }

    @NotNull Furnace FURNACE = new Furnace();

    static @NotNull Furnace furnace() {
        return Views.FURNACE;
    }

    /**
     * Provides a view into a furnace
     * @param view a contiguous section of slots 0 to 3
     * @param input {@link Furnace#VIEW} slot 0
     * @param fuel {@link Furnace#VIEW} slot 1
     * @param output {@link Furnace#VIEW} slot 2
     */
    record Furnace(@NotNull InventoryView view,
                   @NotNull InventoryView.Singular input,
                   @NotNull InventoryView.Singular fuel,
                   @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 3);
        public static final @NotNull InventoryView.Singular INPUT = Furnace.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular FUEL = Furnace.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular OUTPUT = Furnace.VIEW.fork(2);

        public Furnace() {
            this(VIEW, INPUT, FUEL, OUTPUT);
        }
    }

    @NotNull Grindstone GRINDSTONE = new Grindstone();

    static @NotNull Grindstone grindstone() {
        return Views.GRINDSTONE;
    }

    /**
     * Provides a view into a grindstone
     * @param view a contiguous section of slots 0 to 3
     * @param input {@link Grindstone#VIEW} slots 0 to 2
     * @param output {@link Grindstone#VIEW} slot 2
     */
    record Grindstone(@NotNull InventoryView view,
                      @NotNull InventoryView input,
                      @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 3);
        public static final @NotNull InventoryView INPUT = Grindstone.VIEW.forkRange(0, 2);
        public static final @NotNull InventoryView.Singular OUTPUT = Grindstone.VIEW.fork(2);

        public Grindstone() {
            this(VIEW, INPUT, OUTPUT);
        }
    }

    @NotNull Hopper HOPPER = new Hopper();

    static @NotNull Hopper hopper() {
        return Views.HOPPER;
    }

    /**
     * Provides a view into a hopper
     * @param view a contiguous section of slots 0 to 5
     * @param storage {@link Hopper#VIEW} slots 0 to 5
     */
    record Hopper(@NotNull InventoryView view,
                  @NotNull InventoryView storage) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 5);
        public static final @NotNull InventoryView STORAGE = Hopper.VIEW.forkRange(0, 5);

        public Hopper() {
            this(VIEW, STORAGE);
        }
    }

    @NotNull Lectern LECTERN = new Lectern();

    static @NotNull Lectern lectern() {
        return Views.LECTERN;
    }

    /**
     * Provides a view into a lectern
     * @param view slot 0
     * @param book {@link Lectern#VIEW} slot 0
     */
    record Lectern(@NotNull InventoryView view,
                   @NotNull InventoryView.Singular book) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.singular(0);
        public static final @NotNull InventoryView.Singular BOOK = Lectern.VIEW.fork(0);

        public Lectern() {
            this(VIEW, BOOK);
        }
    }

    @NotNull Loom LOOM = new Loom();

    static @NotNull Loom loom() {
        return Views.LOOM;
    }

    /**
     * Provides a view into a loom
     * @param view a contiguous section of slots 0 to 4
     * @param banner {@link Loom#VIEW} slot 0
     * @param color {@link Loom#VIEW} slot 1
     * @param pattern {@link Loom#VIEW} slot 2
     * @param output {@link Loom#VIEW} slot 3
     */
    record Loom(@NotNull InventoryView view,
                @NotNull InventoryView.Singular banner,
                @NotNull InventoryView.Singular color,
                @NotNull InventoryView.Singular pattern,
                @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 4);
        public static final @NotNull InventoryView.Singular BANNER = Loom.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular COLOR = Loom.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular PATTERN = Loom.VIEW.fork(2);
        public static final @NotNull InventoryView.Singular OUTPUT = Loom.VIEW.fork(3);

        public Loom() {
            this(VIEW, BANNER, COLOR, PATTERN, OUTPUT);
        }
    }

    @NotNull Merchant MERCHANT = new Merchant();

    static @NotNull Merchant merchant() {
        return Views.MERCHANT;
    }

    /**
     * Provides a view into a merchant's (villager, wandering trader, etc) trade menu
     * @param view a contiguous section of slots 0 to 3
     * @param input see {@link Input}
     * @param output {@link Merchant#VIEW} slot 2
     */
    record Merchant(@NotNull InventoryView view,
                    @NotNull Input input,
                    @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 3);
        public static final @NotNull Input INPUT = new Input();
        public static final @NotNull InventoryView.Singular OUTPUT = Merchant.VIEW.fork(2);

        public Merchant() {
            this(VIEW, INPUT, OUTPUT);
        }

        /**
         * Provides a view into the input slots of a merchant's inventory
         * @param view {@link Merchant#VIEW} slots 0 to 2
         * @param left {@link Input#VIEW} slot 0
         * @param right {@link Input#VIEW} slot 1
         */
        public record Input(@NotNull InventoryView view,
                            @NotNull InventoryView.Singular left,
                            @NotNull InventoryView.Singular right) implements DelegateView {
            public static final @NotNull InventoryView VIEW = Merchant.VIEW.forkRange(0, 2);
            public static final @NotNull InventoryView.Singular LEFT = Input.VIEW.fork(0);
            public static final @NotNull InventoryView.Singular RIGHT = Input.VIEW.fork(1);

            public Input() {
                this(VIEW, LEFT, RIGHT);
            }
        }
    }

    @NotNull Player PLAYER = new Player();

    static @NotNull Player player() {
        return Views.PLAYER;
    }

    /**
     * Provides a view into a player's inventory
     * @param view a contiguous section of slots 0 to 46
     * @param contents see {@link Contents}
     * @param crafting see {@link Crafting}
     * @param armor see {@link Armor}
     * @param offhand {@link Player#VIEW} slot 45
     */
    record Player(@NotNull InventoryView view,
                  @NotNull Contents contents,
                  @NotNull Crafting crafting,
                  @NotNull Armor armor,
                  @NotNull InventoryView.Singular offhand) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 46);
        public static final @NotNull Contents CONTENTS = new Contents();
        public static final @NotNull Crafting CRAFTING = new Crafting();
        public static final @NotNull Armor ARMOR = new Armor();
        public static final @NotNull InventoryView.Singular OFFHAND = Player.VIEW.fork(45);

        public Player() {
            this(VIEW, CONTENTS, CRAFTING, ARMOR, OFFHAND);
        }

        /**
         * Provides a view into the main contents of a player's inventory
         * @param view {@link Player#VIEW} slots 0 to 36
         * @param hotbar {@link Contents#VIEW} slots 0 to 9
         * @param storage {@link Contents#VIEW} slots 9 to 36
         */
        public record Contents(@NotNull InventoryView view,
                               @NotNull InventoryView hotbar,
                               @NotNull InventoryView storage) implements DelegateView {
            public static final @NotNull InventoryView VIEW = Player.VIEW.forkRange(0, 36);
            public static final @NotNull InventoryView HOTBAR = Contents.VIEW.forkRange(0, 9);
            public static final @NotNull InventoryView STORAGE = Contents.VIEW.forkRange(9, 36);

            public Contents() {
                this(VIEW, HOTBAR, STORAGE);
            }
        }

        /**
         * Provides a view into the crafting section of a player's inventory
         * @param view {@link Player#VIEW} slots 36 to 41
         * @param output {@link Crafting#VIEW} slot 0
         * @param input {@link Crafting#VIEW} slots 1 to 5
         */
        public record Crafting(@NotNull InventoryView view,
                               @NotNull InventoryView.Singular output,
                               @NotNull InventoryView input) implements DelegateView {
            public static final @NotNull InventoryView VIEW = Player.VIEW.forkRange(36, 41);
            public static final @NotNull InventoryView.Singular OUTPUT = Crafting.VIEW.fork(0);
            public static final @NotNull InventoryView INPUT = Crafting.VIEW.forkRange(1, 5);

            public Crafting() {
                this(VIEW, OUTPUT, INPUT);
            }
        }

        /**
         * Provides a view into the armor section of a player's inventory
         * @param view {@link Player#VIEW} slots 41 to 45
         * @param helmet {@link Armor#VIEW} slot 0
         * @param chestplate {@link Armor#VIEW} slot 1
         * @param leggings {@link Armor#VIEW} slot 2
         * @param boots {@link Armor#VIEW} slot 3
         */
        public record Armor(@NotNull InventoryView view,
                            @NotNull InventoryView.Singular helmet,
                            @NotNull InventoryView.Singular chestplate,
                            @NotNull InventoryView.Singular leggings,
                            @NotNull InventoryView.Singular boots) implements DelegateView {
            public static final @NotNull InventoryView VIEW = Player.VIEW.forkRange(41, 45);
            public static final @NotNull InventoryView.Singular HELMET = Armor.VIEW.fork(0);
            public static final @NotNull InventoryView.Singular CHESTPLATE = Armor.VIEW.fork(1);
            public static final @NotNull InventoryView.Singular LEGGINGS = Armor.VIEW.fork(2);
            public static final @NotNull InventoryView.Singular BOOTS = Armor.VIEW.fork(3);

            public Armor() {
                this(VIEW, HELMET, CHESTPLATE, LEGGINGS, BOOTS);
            }
        }
    }

    @NotNull ShulkerBox SHULKER_BOX = new ShulkerBox();

    static @NotNull ShulkerBox shulkerBox() {
        return Views.SHULKER_BOX;
    }

    /**
     * Provides a view into a shulker box
     * @param view a contiguous section of slots 0 to 27
     * @param storage {@link ShulkerBox#VIEW} slots 0 to 27
     */
    record ShulkerBox(@NotNull InventoryView view,
                      @NotNull InventoryView storage) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 27);
        public static final @NotNull InventoryView STORAGE = ShulkerBox.VIEW.forkRange(0, 27);

        public ShulkerBox() {
            this(VIEW, STORAGE);
        }
    }

    @NotNull Smithing SMITHING = new Smithing();

    static @NotNull Smithing smithing() {
        return Views.SMITHING;
    }

    /**
     * Provides a view into a smithing table
     * @param view a contiguous section of slots 0 to 3
     * @param template {@link Smithing#VIEW} slot 0
     * @param base {@link Smithing#VIEW} slot 1
     * @param addition {@link Smithing#VIEW} slot 2
     * @param output {@link Smithing#VIEW} slot 3
     */
    record Smithing(@NotNull InventoryView view,
                    @NotNull InventoryView.Singular template,
                    @NotNull InventoryView.Singular base,
                    @NotNull InventoryView.Singular addition,
                    @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 4);
        public static final @NotNull InventoryView.Singular TEMPLATE = Smithing.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular BASE = Smithing.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular ADDITION = Smithing.VIEW.fork(2);
        public static final @NotNull InventoryView.Singular OUTPUT = Smithing.VIEW.fork(3);

        public Smithing() {
            this(VIEW, TEMPLATE, BASE, ADDITION, OUTPUT);
        }
    }

    @NotNull Smoker SMOKER = new Smoker();

    static @NotNull Smoker smoker() {
        return Views.SMOKER;
    }

    /**
     * Provides a view into a smoker
     * @param view a contiguous section of slots 0 to 3
     * @param input {@link Smoker#VIEW} slot 0
     * @param fuel {@link Smoker#VIEW} slot 1
     * @param output {@link Smoker#VIEW} slot 2
     */
    record Smoker(@NotNull InventoryView view,
                  @NotNull InventoryView.Singular input,
                  @NotNull InventoryView.Singular fuel,
                  @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 3);
        public static final @NotNull InventoryView.Singular INPUT = Smoker.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular FUEL = Smoker.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular OUTPUT = Smoker.VIEW.fork(2);

        public Smoker() {
            this(VIEW, INPUT, FUEL, OUTPUT);
        }
    }

    @NotNull Stonecutter STONECUTTER = new Stonecutter();

    static @NotNull Stonecutter stonecutter() {
        return Views.STONECUTTER;
    }

    /**
     * Provides a view into a stonecutter
     * @param view a contiguous section of slots 0 to 2
     * @param input {@link Stonecutter#VIEW} slot 0
     * @param output {@link Stonecutter#VIEW} slot 1
     */
    record Stonecutter(@NotNull InventoryView view,
                       @NotNull InventoryView.Singular input,
                       @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 2);
        public static final @NotNull InventoryView.Singular INPUT = Stonecutter.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular OUTPUT = Stonecutter.VIEW.fork(1);

        public Stonecutter() {
            this(VIEW, INPUT, OUTPUT);
        }
    }

    /**
     * View delegate class that makes implementation code shorter.
     */
    interface DelegateView extends InventoryView {
        @NotNull InventoryView view();

        @Override
        default int size() {
            return view().size();
        }

        @Override
        default int localToExternal(int localSlot) {
            return view().localToExternal(localSlot);
        }

        @Override
        default int externalToLocal(int externalSlot) {
            return view().externalToLocal(externalSlot);
        }

        @Override
        default boolean isValidExternal(int externalSlot) {
            return view().isValidExternal(externalSlot);
        }
    }

}
