package com.payne.games.actions;

import com.payne.games.actions.commands.*;
import com.payne.games.gameObjects.GameObject;
import com.payne.games.gameObjects.actors.Actor;
import com.payne.games.gameObjects.statics.entities.Chest;
import com.payne.games.gameObjects.statics.entities.Door;
import com.payne.games.inventory.IPickable;
import com.payne.games.map.tiles.Tile;


/**
 * Used to assign actions to different GameObjects.<br>
 * Decouples the GameObjects from the Actions themselves.
 */
public class ActionIssuer {

    public ActionIssuer() {
    }



    public void interactiveMove(Actor source, GameObject recipient, Tile from, Tile next, Tile to) {
        source.addAction(new InteractiveMoveAction(source, recipient, from, next, to));
    }

    public void move(Actor source, Tile from, Tile next, Tile to) {
        source.addAction(new MoveAction(source, from, next, to));
    }

    public void attack(Actor source, Actor target) {
        source.addAction(new AttackAction(source, target, source.getDmg()));
    }

    public void openChest(Actor source, Chest target) {
        source.addAction(new OpenChestAction(source, target));
    }

    public void toggleDoor(Actor source, Door target) {
        source.addAction(new ToggleDoorAction(source, target));
    }

    public void unlockDoor(Actor source, Door target) {
        source.addAction(new UnlockDoorAction(source, target));
    }

    public void pickUp(Actor source, IPickable object) {
        source.addAction(new PickUpAction(source, object));
    }

    public void dropItem(Actor source, IPickable object) {
        source.addAction(new DropAction(source, object));
    }

    public void skipTurn(Actor actor) {
        actor.addAction(new NoopAction(actor));
    }
}
