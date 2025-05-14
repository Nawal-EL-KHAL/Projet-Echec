public class DemoPlateau extends PlateauEchecs {

    final Plateau plateau;

    public DemoPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public void demoPromotion() {
        // Pion blanc prêt à être promu
        plateau.placerPiece(new DecorateurPion(new PieceNeutre(true, Type.Pion)), 1, 4);

        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 7);

        // Roi noir
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 0);

        relationVue();
    }

    public void demoEchec() {
        // Roi noir
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 4);

        // Pion noir
        plateau.placerPiece(new DecorateurPion(new PieceNeutre(false, Type.Pion)), 1, 4);

        // Reine blanche qui attaque mais le roi peut encore fuir
        plateau.placerPiece(new DecorateurDiagonale(new DecorateurLigne(new PieceNeutre(true, Type.Reine))), 2, 5);

        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 7);

        relationVue();
    }

    public void demoEchecEtMat() {

        // Tour blanche
        plateau.placerPiece(new DecorateurLigne(new PieceNeutre(true, Type.Tour)), 1, 7);

        // Pion noir
        plateau.placerPiece((new PieceNeutre(false, Type.Pion)), 1, 0);

        // Pion noir
        plateau.placerPiece((new PieceNeutre(false, Type.Pion)), 1, 1);

        // Roi noir
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 0);

        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 7);

        relationVue();
    }

    public void demoPriseEnPassant() {
        Piece pionBlanc = new DecorateurPion(new PieceNeutre(true, Type.Pion));
        Piece pionNoir = new DecorateurPion(new PieceNeutre(false, Type.Pion));
        Piece roiBlanc = new DecorateurRoi(new PieceNeutre(true, Type.Roi));
        Piece roiNoir = new DecorateurRoi(new PieceNeutre(false, Type.Roi));

        // Placer les rois pour éviter les erreurs d’échec
        plateau.placerPiece(roiBlanc, 7, 4);
        plateau.placerPiece(roiNoir, 0, 4);

        // Le pion blanc vient de faire un double pas de (6,4) à (4,4)
        plateau.placerPiece(pionBlanc, 6, 4);

        // Le pion noir est en position de capture en passant à gauche du pion blanc
        plateau.placerPiece(pionNoir, 4, 3);

        relationVue();
    }

    public void demoCoupIllegal() {
        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 4, 3);

        // Cavalier noir
        plateau.placerPiece(new DecorateurCavalier(new PieceNeutre(false, Type.Cavalier)), 4, 4);

        // Tour noire
        plateau.placerPiece(new DecorateurLigne(new PieceNeutre(false, Type.Tour)), 0, 4);

        relationVue();
    }

    public void demoCoupIllegal2() {
        // Roi blanc
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 4, 3);

        // Cavalier blanc
        plateau.placerPiece(new DecorateurCavalier(new PieceNeutre(true, Type.Cavalier)), 3, 3);

        // Tour noire
        plateau.placerPiece(new DecorateurLigne(new PieceNeutre(false, Type.Tour)), 0, 3);

        relationVue();
    }

    public void demoRoque() {

        // Pièces noires
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(false, Type.Roi)), 0, 4);
        plateau.placerPiece(new DecorateurLigne(new PieceNeutre(false, Type.Tour)), 0, 0);
        plateau.placerPiece(new DecorateurLigne(new PieceNeutre(false, Type.Tour)), 0, 7);
        for (int i = 0; i < 8; i++) {
            plateau.placerPiece(new DecorateurPion(new PieceNeutre(false, Type.Pion)), 1, i);
        }
        // Pièces blanches
        plateau.placerPiece(new DecorateurRoi(new PieceNeutre(true, Type.Roi)), 7, 4);
        plateau.placerPiece(new DecorateurLigne(new PieceNeutre(true, Type.Tour)), 7, 0);
        plateau.placerPiece(new DecorateurLigne(new PieceNeutre(true, Type.Tour)), 7, 7);
        for (int i = 0; i < 8; i++) {
            plateau.placerPiece(new DecorateurPion(new PieceNeutre(true, Type.Pion)), 6, i);
        }
        relationVue();
    }

}
