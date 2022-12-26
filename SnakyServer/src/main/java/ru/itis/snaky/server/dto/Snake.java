package ru.itis.snaky.server.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Snake {

    private List<Integer[]> bodyCoordinates;
    private String snakeName;
    private Color color;

    private String direction;

    public void move() {

        for (int i = bodyCoordinates.size() - 1; i >= 0; i--) {
            if (i == 0) {
                switch (direction) {
                    case "UP":
                        bodyCoordinates.set(i, new Integer[]{bodyCoordinates.get(i)[0], bodyCoordinates.get(i)[1] - 1});
                        break;
                    case "LEFT":
                        bodyCoordinates.set(i, new Integer[]{bodyCoordinates.get(i)[0] - 1, bodyCoordinates.get(i)[1]});
                        break;
                    case "DOWN":
                        bodyCoordinates.set(i, new Integer[]{bodyCoordinates.get(i)[0], bodyCoordinates.get(i)[1] + 1});
                        break;
                    case "RIGHT":
                        bodyCoordinates.set(i, new Integer[]{bodyCoordinates.get(i)[0] + 1, bodyCoordinates.get(i)[1]});
                        break;
                }
            } else {
                bodyCoordinates.set(i, bodyCoordinates.get(i - 1));
            }
        }
    }

    public Integer[] getHead() {
        return bodyCoordinates.get(0);
    }

    public void increase() {
        Integer[] tailCube = bodyCoordinates.get(bodyCoordinates.size() - 1);
        Integer[] preTailCube = bodyCoordinates.get(bodyCoordinates.size() - 2);
        if (tailCube[1] == preTailCube[1]) {
            if (tailCube[0] > preTailCube[0]) {
                bodyCoordinates.add(new Integer[]{tailCube[0] + 1, tailCube[1]});
            } else {
                bodyCoordinates.add(new Integer[]{tailCube[0] - 1, tailCube[1]});
            }
        } else if (tailCube[0] == preTailCube[0]) {
            if (tailCube[1] > preTailCube[1]) {
                bodyCoordinates.add(new Integer[]{tailCube[0], tailCube[1] + 1});
            } else {
                bodyCoordinates.add(new Integer[]{tailCube[0], tailCube[1] - 1});
            }
        }
    }
}