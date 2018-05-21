import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class JogadorTeste extends Jogador {
    private ArrayList<CartaLacaio> lacaios;
    private ArrayList<CartaLacaio> lacaiosOponente;
    private static Random aleatorio = new Random(0L);
    private static double alfa = 1.0D;
    private static double beta = 0.75D;
    private EnumSet<Funcionalidade> funcionalidadesAtivas;

    public JogadorTeste(ArrayList<Carta> maoInicial, boolean primeiro, EnumSet<Funcionalidade> funcionalidades) {
        this.primeiroJogador = primeiro;
        this.mao = maoInicial;
        this.lacaios = new ArrayList();
        this.lacaiosOponente = new ArrayList();
        this.funcionalidadesAtivas = EnumSet.noneOf(Funcionalidade.class);
        this.funcionalidadesAtivas.addAll(funcionalidades);
        //System.out.println("Sou o " + (primeiro?"primeiro":"segundo") + " jogador (classe: JogadorTeste)");
        //System.out.println("Mao inicial:");

        for(int i = 0; i < this.mao.size(); ++i) {
            //System.out.println("ID " + ((Carta)this.mao.get(i)).getID() + ": " + this.mao.get(i));
        }

        //System.out.println("Funcionalidade ativas:");
        //System.out.println("INVESTIDA: " + (this.funcionalidadesAtivas.contains(Funcionalidade.INVESTIDA)?"SIM":"NAO"));
        //System.out.println("ATAQUE_DUPLO: " + (this.funcionalidadesAtivas.contains(Funcionalidade.ATAQUE_DUPLO)?"SIM":"NAO"));
        //System.out.println("PROVOCAR: " + (this.funcionalidadesAtivas.contains(Funcionalidade.PROVOCAR)?"SIM":"NAO"));
    }

    public ArrayList<Jogada> processarTurno(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente) {
        if(cartaComprada != null) {
            this.mao.add(cartaComprada);
        }

        int minhaMana;
        int minhaVida;
        if(this.primeiroJogador) {
            minhaMana = mesa.getManaJog1();
            minhaVida = mesa.getVidaHeroi1();
            this.lacaios = mesa.getLacaiosJog1();
            this.lacaiosOponente = mesa.getLacaiosJog2();
        } else {
            minhaMana = mesa.getManaJog2();
            minhaVida = mesa.getVidaHeroi2();
            this.lacaios = mesa.getLacaiosJog2();
            this.lacaiosOponente = mesa.getLacaiosJog1();
        }

        ArrayList minhasJogadas = new ArrayList();
        ArrayList possiveisDrops = new ArrayList();
        ArrayList possiveisDrops_CartasIdx = new ArrayList();
        int numMeusLacaiosLimite = this.lacaios.size();
        int k;
        int idxProvocar;
        Jogada var23;
        if(alfa >= aleatorio.nextDouble()) {
            for(k = 0; k < this.mao.size(); ++k) {
                Carta meuLacaio = (Carta)this.mao.get(k);
                if(meuLacaio instanceof CartaLacaio && meuLacaio.getMana() <= minhaMana && numMeusLacaiosLimite < 7) {
                    var23 = new Jogada(TipoJogada.LACAIO, meuLacaio, (Carta)null);
                    possiveisDrops.add(var23);
                    possiveisDrops_CartasIdx.add(new Integer(k));
                } else if(meuLacaio instanceof CartaMagia && meuLacaio.getMana() <= minhaMana && ((CartaMagia)meuLacaio).getMagiaTipo() != TipoMagia.BUFF) {
                    int numAtaques = aleatorio.nextInt(this.lacaiosOponente.size() + 1);
                    Jogada vivo;
                    if(numAtaques == this.lacaiosOponente.size()) {
                        vivo = new Jogada(TipoJogada.MAGIA, meuLacaio, (Carta)null);
                    } else {
                        vivo = new Jogada(TipoJogada.MAGIA, meuLacaio, (Carta)this.lacaiosOponente.get(numAtaques));
                    }

                    possiveisDrops.add(vivo);
                    possiveisDrops_CartasIdx.add(new Integer(k));
                }
            }

            label206:
            while(true) {
                while(true) {
                    if(possiveisDrops.size() <= 0) {
                        break label206;
                    }

                    k = aleatorio.nextInt(possiveisDrops.size());
                    if(k < possiveisDrops.size()) {
                        Jogada var21 = (Jogada)possiveisDrops.get(k);
                        minhaMana -= ((Carta)this.mao.get(((Integer)possiveisDrops_CartasIdx.get(k)).intValue())).getMana();
                        Carta var24 = (Carta)this.mao.get(((Integer)possiveisDrops_CartasIdx.get(k)).intValue());
                        this.mao.remove(((Integer)possiveisDrops_CartasIdx.get(k)).intValue());
                        minhasJogadas.add(var21);
                        if(this.funcionalidadesAtivas.contains(Funcionalidade.INVESTIDA) && var21.getTipo() == TipoJogada.LACAIO) {
                            Carta var27 = var21.getCartaJogada();
                            if(((CartaLacaio)var27).getEfeito() == TipoEfeito.INVESTIDA) {
                                this.lacaios.add((CartaLacaio)var27);
                            }
                        }

                        if(var24 instanceof CartaMagia) {
                            if(((CartaMagia)var24).getMagiaTipo() != TipoMagia.ALVO && ((CartaMagia)var24).getMagiaTipo() != TipoMagia.AREA) {
                                CartaLacaio var29 = (CartaLacaio)this.lacaios.get(this.lacaios.indexOf(var21.getCartaAlvo()));
                                var29.setAtaque(var29.getAtaque() + 2);
                                var29.setVidaAtual(var29.getVidaAtual() + 2);
                                var29.setVidaMaxima(var29.getVidaMaxima() + 2);
                            } else if(var21.getCartaAlvo() != null) {
                                this.processaDanosMagia((CartaMagia)var24, var21.getCartaAlvo().getID());
                            } else {
                                this.processaDanosMagia((CartaMagia)var24, -1);
                            }
                        } else {
                            ++numMeusLacaiosLimite;
                        }

                        possiveisDrops.clear();
                        possiveisDrops_CartasIdx.clear();

                        for(int var31 = 0; var31 < this.mao.size(); ++var31) {
                            Carta w = (Carta)this.mao.get(var31);
                            Jogada lacaioProvocar;
                            if(w instanceof CartaLacaio && w.getMana() <= minhaMana && numMeusLacaiosLimite < 7) {
                                lacaioProvocar = new Jogada(TipoJogada.LACAIO, w, (Carta)null);
                                possiveisDrops.add(lacaioProvocar);
                                possiveisDrops_CartasIdx.add(new Integer(var31));
                            } else if(w instanceof CartaMagia && w.getMana() <= minhaMana) {
                                if(((CartaMagia)w).getMagiaTipo() == TipoMagia.BUFF) {
                                    if(this.lacaios.size() > 0) {
                                        idxProvocar = aleatorio.nextInt(this.lacaios.size());
                                        lacaioProvocar = new Jogada(TipoJogada.MAGIA, w, (Carta)this.lacaios.get(idxProvocar));
                                        possiveisDrops.add(lacaioProvocar);
                                        possiveisDrops_CartasIdx.add(new Integer(var31));
                                    }
                                } else {
                                    int var33 = aleatorio.nextInt(this.lacaiosOponente.size() + 1);
                                    Jogada var35;
                                    if(var33 == this.lacaiosOponente.size()) {
                                        var35 = new Jogada(TipoJogada.MAGIA, w, (Carta)null);
                                    } else {
                                        var35 = new Jogada(TipoJogada.MAGIA, w, (Carta)this.lacaiosOponente.get(var33));
                                    }

                                    possiveisDrops.add(var35);
                                    possiveisDrops_CartasIdx.add(new Integer(var31));
                                }
                            }
                        }
                    } else {
                        possiveisDrops.clear();
                        possiveisDrops_CartasIdx.clear();
                    }
                }
            }
        }

        if(minhaMana >= 2 && this.lacaiosOponente.size() > 0) {
            boolean var20 = false;

            int var22;
            for(var22 = 0; var22 < this.lacaiosOponente.size(); ++var22) {
                CartaLacaio var25 = (CartaLacaio)this.lacaiosOponente.get(var22);
                if(var25.getEfeito() == TipoEfeito.PROVOCAR) {
                    var20 = true;
                    break;
                }
            }

            if(!var20) {
                var22 = aleatorio.nextInt(this.lacaiosOponente.size() + 1);
                if(var22 == this.lacaiosOponente.size()) {
                    var22 = -1;
                    var23 = new Jogada(TipoJogada.PODER, (Carta)null, (Carta)null);
                } else if(minhaVida <= ((CartaLacaio)this.lacaiosOponente.get(var22)).getAtaque()) {
                    var23 = new Jogada(TipoJogada.PODER, (Carta)null, (Carta)null);
                } else {
                    var23 = new Jogada(TipoJogada.PODER, (Carta)null, (Carta)this.lacaiosOponente.get(var22));
                    int var10000 = minhaVida - ((CartaLacaio)this.lacaiosOponente.get(var22)).getAtaque();
                }

                minhasJogadas.add(var23);
                minhaMana -= 2;
                if(var22 != -1) {
                    ((CartaLacaio)this.lacaiosOponente.get(var22)).setVidaAtual(((CartaLacaio)this.lacaiosOponente.get(var22)).getVidaAtual() - 1);
                    if(((CartaLacaio)this.lacaiosOponente.get(var22)).getVidaAtual() <= 0) {
                        this.lacaiosOponente.remove(var22);
                    }
                }
            }
        }

        for(k = 0; k < this.lacaios.size(); ++k) {
            CartaLacaio var26 = (CartaLacaio)this.lacaios.get(k);
            byte var30 = 1;
            boolean var32 = true;
            if(var26.getVidaAtual() <= 0) {
                var32 = false;
            }

            if(this.funcionalidadesAtivas.contains(Funcionalidade.ATAQUE_DUPLO) && var26.getEfeito() == TipoEfeito.ATAQUE_DUPLO) {
                var30 = 2;
            }

            for(int var28 = 0; var28 < var30 && var32; ++var28) {
                CartaLacaio var34 = null;
                idxProvocar = 0;

                int alvoRand;
                CartaLacaio lacaioOpo;
                for(alvoRand = 0; alvoRand < this.lacaiosOponente.size(); ++alvoRand) {
                    lacaioOpo = (CartaLacaio)this.lacaiosOponente.get(alvoRand);
                    if(lacaioOpo.getEfeito() == TipoEfeito.PROVOCAR) {
                        var34 = lacaioOpo;
                        idxProvocar = alvoRand;
                        break;
                    }
                }

                Jogada var36;
                if(this.funcionalidadesAtivas.contains(Funcionalidade.PROVOCAR) && var34 != null) {
                    var36 = new Jogada(TipoJogada.ATAQUE, var26, var34);
                    minhasJogadas.add(var36);
                    this.processaDanosAtaque(var26, var34, idxProvocar);
                    if(var26.getVidaAtual() <= 0) {
                        var32 = false;
                    }
                } else if(beta < aleatorio.nextDouble() && this.lacaiosOponente.size() != 0) {
                    alvoRand = aleatorio.nextInt(this.lacaiosOponente.size());
                    lacaioOpo = (CartaLacaio)this.lacaiosOponente.get(alvoRand);
                    Jogada ataque = new Jogada(TipoJogada.ATAQUE, var26, lacaioOpo);
                    minhasJogadas.add(ataque);
                    this.processaDanosAtaque(var26, lacaioOpo, alvoRand);
                    if(var26.getVidaAtual() <= 0) {
                        var32 = false;
                    }
                } else {
                    var36 = new Jogada(TipoJogada.ATAQUE, var26, (Carta)null);
                    minhasJogadas.add(var36);
                }
            }
        }

        return minhasJogadas;
    }

    private void processaDanosMagia(CartaMagia magiaUsada, int alvoID) {
        int i;
        if(magiaUsada.getMagiaTipo() != TipoMagia.ALVO) {
            for(i = 0; i < this.lacaiosOponente.size(); ++i) {
                ((CartaLacaio)this.lacaiosOponente.get(i)).setVidaAtual(((CartaLacaio)this.lacaiosOponente.get(i)).getVidaAtual() - magiaUsada.getMagiaDano());
                if(((CartaLacaio)this.lacaiosOponente.get(i)).getVidaAtual() <= 0) {
                    this.lacaiosOponente.remove(i);
                    --i;
                }
            }
        } else if(alvoID != -1) {
            for(i = 0; i < this.lacaiosOponente.size() && ((CartaLacaio)this.lacaiosOponente.get(i)).getID() != alvoID; ++i) {
                ;
            }

            if(i >= this.lacaiosOponente.size() || ((CartaLacaio)this.lacaiosOponente.get(i)).getID() != alvoID) {
                throw new RuntimeException("Erro JogadorAleatorio: Tentou usar magia em alvo invalido. Alvo id:" + ((CartaLacaio)this.lacaiosOponente.get(i)).getID());
            }

            ((CartaLacaio)this.lacaiosOponente.get(i)).setVidaAtual(((CartaLacaio)this.lacaiosOponente.get(i)).getVidaAtual() - magiaUsada.getMagiaDano());
            if(((CartaLacaio)this.lacaiosOponente.get(i)).getVidaAtual() <= 0) {
                this.lacaiosOponente.remove(i);
            }
        }

    }

    private void processaDanosAtaque(CartaLacaio origemAtk, CartaLacaio destinoAtk, int idxDestino) {
        destinoAtk.setVidaAtual(destinoAtk.getVidaAtual() - origemAtk.getAtaque());
        origemAtk.setVidaAtual(origemAtk.getVidaAtual() - destinoAtk.getAtaque());
        if(destinoAtk.getVidaAtual() <= 0) {
            this.lacaiosOponente.remove(idxDestino);
        }

    }
}