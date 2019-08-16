package com.payne.games.inputProcessors;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;


public class MyInputMultiplexer extends InputMultiplexer {

    public MyInputMultiplexer(InputProcessor... processors) {
        super(processors);
    }
}
