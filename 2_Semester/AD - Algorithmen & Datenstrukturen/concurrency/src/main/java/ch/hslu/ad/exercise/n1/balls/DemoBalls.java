/*
 * Copyright 2025 Hochschule Luzern - Informatik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.ad.exercise.n1.balls;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Demonstration von Bällen.
 */
public final class DemoBalls {
    /**
     * Privater Konstruktor.
     */
    private DemoBalls() {
    }

    /**
     * Main-Demo.
     *
     * @param args not used.
     */
    public static void main(final String[] args) {

        final String[] color = {"red", "black", "blue", "yellow", "green", "magenta"};

        Random random = new Random();

        System.out.println(random.nextInt(10));

        for(int i = 0; i < 50; i++){
            Thread thread = new Thread(new Ball(random.nextInt(20, 50), random.nextInt(600), random.nextInt(400), color[random.nextInt(color.length)]), "Ball " + i);
            thread.start();
        }
    }
}
