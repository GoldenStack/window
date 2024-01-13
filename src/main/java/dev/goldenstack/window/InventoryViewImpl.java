package dev.goldenstack.window;

import it.unimi.dsi.fastutil.ints.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class InventoryViewImpl {

    // Implements InventoryView.Singular so that it can be treated as it when needed
    //  (i.e. when we know that it must be singular, and so code duplication can be avoided)
    // This is safe because implementing Singular doesn't actually have any side effects; it just adds utility methods.
    //  (if Singular ever gets any actual side effects, this needs to be changed)
    record ContiguousFork(int min, int max) implements InventoryView.Singular {

        ContiguousFork {
            if (min < 0 || max < 0) {
                throw new IllegalArgumentException("Slot IDs cannot be negatively signed!");
            } else if (min > max) {
                throw new IllegalArgumentException("The minimum value cannot be greater than the maximum!");
            }
        }

        @Override
        public int size() {
            return max - min;
        }

        @Override
        public int localToExternal(int localSlot) {
            if (!isValidLocal(localSlot)) {
                return -1;
            }
            return localSlot + min;
        }

        @Override
        public int externalToLocal(int externalSlot) {
            if (!isValidExternal(externalSlot)) {
                return -1;
            }
            return externalSlot - min;
        }

        @Override
        public boolean isValidExternal(int externalSlot) {
            return externalSlot >= min && externalSlot < max;
        }
    }

    // Joins two views together, essentially treating the child as the, well, child of the parent
    // Implements InventoryView.Singular so that it can be treated as it when needed
    //  (i.e. when we know that it must be singular, and so code duplication can be avoided)
    // This is safe because implementing Singular doesn't actually have any side effects; it just adds utility methods.
    //  (if Singular ever gets any actual side effects, this needs to be changed)
    record Joiner(@NotNull InventoryView parent, @NotNull InventoryView child) implements InventoryView.Singular {

        Joiner {
            if (child.size() > parent.size()) {
                throw new IllegalArgumentException("Children cannot be larger than their parents!");
            }
        }

        @Override
        public int size() {
            return child.size();
        }

        @Override
        public int localToExternal(int localSlot) {
            if (!child.isValidLocal(localSlot)) {
                return -1;
            }
            int parentLocal = child.localToExternal(localSlot);
            if (!parent.isValidLocal(parentLocal)) {
                return -1;
            }
            return parent.localToExternal(parentLocal);
        }

        @Override
        public boolean isValidLocal(int localSlot) {
            return child.isValidLocal(localSlot) && parent.isValidLocal(child.localToExternal(localSlot));
        }

        @Override
        public int externalToLocal(int externalSlot) {
            if (!parent.isValidExternal(externalSlot)) {
                return -1;
            }
            int childExternal = parent.externalToLocal(externalSlot);
            if (!child.isValidExternal(childExternal)) {
                return -1;
            }
            return child.externalToLocal(childExternal);
        }

        @Override
        public boolean isValidExternal(int externalSlot) {
            return parent.isValidExternal(externalSlot) && child.isValidExternal(parent.externalToLocal(externalSlot));
        }
    }

    record Union(@NotNull List<InventoryView> views) implements InventoryView.Singular {

        Union {
            views = List.copyOf(views);
        }

        @Override
        public int size() {
            return switch(views.size()) {
                case 0 -> 0;
                case 1 -> views.get(0).size();
                default -> {
                    int sum = 0;
                    for (var view : views) {
                        sum += view.size();
                    }
                    yield sum;
                }
            };
        }

        @Override
        public int localToExternal(int localSlot) {
            if (!isValidLocal(localSlot)) {
                return -1;
            }
            for (var view : views) {
                if (localSlot < view.size()) {
                    return view.localToExternal(localSlot);
                }
                localSlot -= view.size();
            }
            // this code should never be run because, at this point, `slot >= size()`
            // must be true, but we already verified that it's false at the start.
            return -1;
        }

        @Override
        public int externalToLocal(int externalSlot) {
            int offset = 0;
            for (var view : views) {
                if (view.isValidExternal(externalSlot)) {
                    return offset + view.externalToLocal(externalSlot);
                }
                offset += view.size();
            }
            return -1;
        }

        @Override
        public boolean isValidExternal(int externalSlot) {
            for (var view : views) {
                if (view.isValidExternal(externalSlot)) {
                    return true;
                }
            }
            return false;
        }
    }

    record Arbitrary(@NotNull IntList localToExternal, @NotNull Int2IntMap externalToLocal) implements InventoryView.Singular {

        Arbitrary(@NotNull IntList localToExternal) {
            this(localToExternal, reverse(localToExternal));
        }

        private static @NotNull Int2IntMap reverse(@NotNull IntList localToExternal) {
            var map = new Int2IntOpenHashMap(localToExternal.size());
            for (int i = 0; i < localToExternal.size(); i++) {
                map.put(localToExternal.getInt(i), i);
            }
            return map;
        }

        Arbitrary {
            // Yes, this doesn't guarantee immutability, but it's close enough as we can control API usages anyway.
            externalToLocal = Int2IntMaps.unmodifiable(externalToLocal);

            localToExternal = new IntImmutableList(localToExternal);
            for (var slot : localToExternal) {
                if (slot < 0) {
                    throw new IllegalArgumentException("Slot IDs cannot be negatively signed!");
                }
            }
        }

        @Override
        public int size() {
            return localToExternal.size();
        }

        @Override
        public int localToExternal(int localSlot) {
            if (!isValidLocal(localSlot)) {
                return -1;
            }
            return localToExternal.getInt(localSlot);
        }

        @Override
        public int externalToLocal(int externalSlot) {
            if (!isValidExternal(externalSlot)) {
                return -1;
            }
            return externalToLocal.get(externalSlot);
        }

        @Override
        public boolean isValidExternal(int externalSlot) {
            return externalToLocal.containsKey(externalSlot);
        }
    }

}
