package dev.goldenstack.window;

import it.unimi.dsi.fastutil.ints.IntImmutableList;
import it.unimi.dsi.fastutil.ints.IntList;
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
    }

    record Arbitrary(@NotNull IntList slots) implements InventoryView.Singular {

        Arbitrary {
            slots = new IntImmutableList(slots);
            for (var slot : slots) {
                if (slot < 0) {
                    throw new IllegalArgumentException("Slot IDs cannot be negatively signed!");
                }
            }
        }

        @Override
        public int size() {
            return slots.size();
        }

        @Override
        public int localToExternal(int localSlot) {
            if (!isValidLocal(localSlot)) {
                return -1;
            }
            return slots.getInt(localSlot);
        }
    }

    // Intended to be used to shorten implementation code
    interface ExtendableRedirect extends InventoryView {
        @NotNull InventoryView view();

        @Override
        default int size() {
            return view().size();
        }

        @Override
        default int localToExternal(int localSlot) {
            return view().localToExternal(localSlot);
        }
    }

}
