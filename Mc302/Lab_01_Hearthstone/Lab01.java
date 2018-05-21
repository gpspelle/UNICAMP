class Lab01 { 

    public static void main(String[] args) { 
        CartaLacaio lacaio1 = new CartaLacaio(1, "Paladino", 1, 1, 1); 
        CartaLacaio lacaio2 = new CartaLacaio(2, "Knight", 2, 2, 2);
        CartaLacaio lacaio3 = new CartaLacaio(3, "Druid", 3, 3, 3);
        CartaLacaio lacaio4 = new CartaLacaio(4, "Sorcerer", 4, 4, 4);
        
        CartaMagia magia1 = new CartaMagia(1, "Sudden Death Rune", 1, true, 1);
        CartaMagia magia2 = new CartaMagia(2, "Ultimate Healing Rune", 2,
                                           false, 2);

        System.out.println("Atiradores: \n" + lacaio1.toString()); 
        System.out.println("Guerreiros: \n" + lacaio2.toString());
        System.out.println("Curandeiros: \n" + lacaio3.toString());
        System.out.println("Magos: \n" + lacaio4.toString());

        System.out.println("Runas: \n" + magia1.toString() + "\n" + 
                                         magia2.toString());
    }
}
