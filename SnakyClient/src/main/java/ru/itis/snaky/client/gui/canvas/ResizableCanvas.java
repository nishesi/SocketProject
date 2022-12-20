package ru.itis.snaky.client.gui.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import lombok.Getter;

public abstract class ResizableCanvas extends Canvas {
    public ResizableCanvas(Pane container) {
        container.getChildren().add(this);
        // Redraw canvas when size changes.

        widthProperty().addListener(evt -> repaint());
        heightProperty().addListener(evt -> repaint());

        container.maxWidthProperty().bind(container.heightProperty());
        container.minWidthProperty().bind(container.heightProperty());

        widthProperty().bind(container.widthProperty());
        heightProperty().bind(container.heightProperty());
    }

    protected abstract void repaint();

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
