package ru.itis.snaky.server.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Snake {

    private List<Cube> bodyCoordinates;
    private String snakeName;
    private Color color;

    private String direction;

    public void move() {

        for (int i = bodyCoordinates.size() - 1; i >= 0; i--) {
            if (i == 0) {
                switch (direction) {
                    case "UP":
                        bodyCoordinates.set(i, new Cube(bodyCoordinates.get(i).getX(), bodyCoordinates.get(i).getY() - 1));
                        break;
                    case "LEFT":
                        bodyCoordinates.set(i, new Cube(bodyCoordinates.get(i).getX() - 1, bodyCoordinates.get(i).getY()));
                        break;
                    case "DOWN":
                        bodyCoordinates.set(i, new Cube(bodyCoordinates.get(i).getX(), bodyCoordinates.get(i).getY() + 1));
                        break;
                    case "RIGHT":
                        bodyCoordinates.set(i, new Cube(bodyCoordinates.get(i).getX() + 1, bodyCoordinates.get(i).getY()));
                        break;
                }
            } else {
                bodyCoordinates.set(i, bodyCoordinates.get(i - 1));
            }
        }
    }

    public Cube getHead() {
        return bodyCoordinates.get(0);
    }

    public void increase() {
        Cube tailCube = bodyCoordinates.get(bodyCoordinates.size() - 1);
        Cube preTailCube = bodyCoordinates.get(bodyCoordinates.size() - 2);
        if (tailCube.getY() == preTailCube.getY()) {
            if (tailCube.getX() > preTailCube.getX()) {
                bodyCoordinates.add(new Cube(tailCube.getX() + 1, tailCube.getY()));
            } else {
                bodyCoordinates.add(new Cube(tailCube.getX() - 1, tailCube.getY()));
            }
        } else if (tailCube.getX() == preTailCube.getX()) {
            if (tailCube.getY() > preTailCube.getY()) {
                bodyCoordinates.add(new Cube(tailCube.getX(), tailCube.getY() + 1));
            } else {
                bodyCoordinates.add(new Cube(tailCube.getX(), tailCube.getY() - 1));
            }
        }
    }
}