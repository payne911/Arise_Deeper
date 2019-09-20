package com.payne.games.rendering.animations;

public interface IObservable {
    void notifyObservers();
    void registerObserver(IObserver observer);
    void unregisterObserver(IObserver observer);
}
