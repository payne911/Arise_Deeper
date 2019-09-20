package com.payne.games.rendering.animations;

public interface IObserver<T> {
    void update();
    void add(T listed);
}
