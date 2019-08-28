package com.payne.games.turns;

import com.badlogic.gdx.utils.BinaryHeap;
import com.payne.games.gameObjects.actors.Actor;


/**
 * Used within the MinHeap that helps the TurnManager figure out the priority of each Actor within a turn.
 */
public class ActorNode extends BinaryHeap.Node {
    public Actor actor;


    /**
     * @param value The initial value for the node. To change the value, use {@link BinaryHeap#add(Node, float)} if the node is
     *              not in the heap, or {@link BinaryHeap#setValue(Node, float)} if the node is in the heap.
     */
    public ActorNode(float value, Actor actor) {
        super(value);
        this.actor = actor;
    }

    public ActorNode(int value, Actor actor) {
        super((float)value);
        this.actor = actor;
    }
}
