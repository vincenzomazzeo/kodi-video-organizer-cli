package com.ninjatech.kodivideoorganizercli.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;

import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.utils.ImageUtils;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class ImageChoiceDialog extends WebDialog {

    private static final EnumMap<TVShowImageType, Integer> IMAGE_TYPE_DIMENSIONS = new EnumMap<TVShowImageType, Integer>(TVShowImageType.class) {

        private static final long serialVersionUID = 1L;

        {
            put(TVShowImageType.BANNER, 100);
            put(TVShowImageType.CHARACTER, 300);
            put(TVShowImageType.CLEARART, 200);
            put(TVShowImageType.FANART, 200);
            put(TVShowImageType.LANDSCAPE, 200);
            put(TVShowImageType.LOGO, 200);
            put(TVShowImageType.POSTER, 350);
            put(TVShowImageType.SEASON_FOLDER, 200);
        }

    };

    public static WebScrollPane makeScrollPane(Component view,
                                               int verticalScrollBarPolicy,
                                               int horizontalScrollBarPolicy) {
        WebScrollPane result = new WebScrollPane(view, false, false);

        result.setVerticalScrollBarPolicy(verticalScrollBarPolicy);
        result.getVerticalScrollBar()
              .setBlockIncrement(30);
        result.getVerticalScrollBar()
              .setUnitIncrement(30);
        result.setHorizontalScrollBarPolicy(horizontalScrollBarPolicy);
        result.getHorizontalScrollBar()
              .setBlockIncrement(30);
        result.getHorizontalScrollBar()
              .setUnitIncrement(30);

        return result;
    }

    private static final long serialVersionUID = 1L;

    private Path selectedPath;

    public ImageChoiceDialog(UI ui) {
        super(ui, true);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImages(WebLookAndFeel.getImages());
        setShowTitleComponent(false);
        setAttachButtons(true);

        WebPanel container = new WebPanel(new BorderLayout());
        container.setBackground(Color.BLACK);
        container.setOpaque(true);
        container.setUndecorated(true);
        container.setMargin(20, 0, 0, 0);
        ComponentMoveAdapter.install(container, this);
        setContentPane(container);
    }

    public void setImages(List<Path> paths, TVShowImageType imageType, int columns) {
        int rows = (int) Math.ceil((double) paths.size() / (double) columns);
        WebPanel imagesPane = new WebPanel(new GridLayout(rows, columns, 10, 10));
        imagesPane.setBackground(Color.BLACK);

        int height = ImageChoiceDialog.IMAGE_TYPE_DIMENSIONS.get(imageType);
        int width = 0;

        for (Path path : paths) {
            BufferedImage image = ImageUtils.loadImage(path.toFile());
            width = height * image.getWidth() / image.getHeight();
            WebDecoratedImage imagePane = new WebDecoratedImage(image.getScaledInstance(width,
                                                                                        height,
                                                                                        Image.SCALE_SMOOTH));
            imagePane.setShadeWidth(5);
            imagePane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            imagePane.setDrawGlassLayer(false);
            imagePane.setOpaque(false);
            imagePane.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent event) {
                    if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
                        ImageChoiceDialog.this.selectedPath = path;
                        ImageChoiceDialog.this.setVisible(false);
                    }
                }

            });
            imagesPane.add(imagePane);
        }

        WebScrollPane gallery = makeScrollPane(imagesPane,
                                               ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                               ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(gallery, BorderLayout.CENTER);
        gallery.setPreferredSize(new Dimension(width * columns + 10 * columns + 50, height + 20));
        pack();
        setLocationRelativeTo(null);
    }

    public Path getSelectedPath() {
        return this.selectedPath;
    }

}
