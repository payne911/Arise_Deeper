package com.payne.games.turns;

import com.badlogic.gdx.utils.BinaryHeap;
import com.payne.games.turns.actions.IAction;


public class ActionNode extends BinaryHeap.Node {
    public IAction action;

    /**
     * @param value The initial value for the node. To change the value, use {@link BinaryHeap#add(Node, float)} if the node is
     *              not in the heap, or {@link BinaryHeap#setValue(Node, float)} if the node is in the heap.
     */
    public ActionNode(float value, IAction action) {
        super(value);
        this.action = action;
    }
}
