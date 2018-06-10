package com.ninjatech.kodivideoorganizercli.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.EnumMap;

import javax.swing.BorderFactory;
import javax.swing.WindowConstants;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.utils.ImageUtils;
import com.ninjatech.kodivideoorganizercli.model.tvshow.TVShowImageType;

public class ImageDialog extends WebDialog {

    private static final EnumMap<TVShowImageType, Integer> IMAGE_TYPE_DIMENSIONS = new EnumMap<TVShowImageType, Integer>(TVShowImageType.class) {

        private static final long serialVersionUID = 1L;

        {
            put(TVShowImageType.BANNER, 185);
            put(TVShowImageType.CHARACTER, 800);
            put(TVShowImageType.CLEARART, 500);
            put(TVShowImageType.FANART, 800);
            put(TVShowImageType.LANDSCAPE, 500);
            put(TVShowImageType.LOGO, 400);
            put(TVShowImageType.POSTER, 500);
            put(TVShowImageType.SEASON_FOLDER, 800);
        }

    };

    private static final long serialVersionUID = 1L;

    public ImageDialog(UI ui) {
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

    public void setImage(Path path, TVShowImageType imageType) {
        int height = ImageDialog.IMAGE_TYPE_DIMENSIONS.get(imageType);
        BufferedImage image = ImageUtils.loadImage(path.toFile());
        int width = height * image.getWidth() / image.getHeight();
        WebDecoratedImage imagePane = new WebDecoratedImage(image.getScaledInstance(width,
                                                                                    height,
                                                                                    Image.SCALE_SMOOTH));
        imagePane.setShadeWidth(5);
        imagePane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imagePane.setDrawGlassLayer(false);
        imagePane.setOpaque(false);
        imagePane.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(imagePane, BorderLayout.CENTER);
        setSize(width, height);
        pack();
        setLocationRelativeTo(null);
    }

}
