package com.gabriel.io;

import com.gabriel.base.cartas.HabilidadesLacaio;
import com.gabriel.base.cartas.Lacaio;
import com.gabriel.base.cartas.magias.Buff;
import com.gabriel.base.cartas.magias.Dano;
import com.gabriel.base.cartas.magias.DanoArea;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Leitor {

    File file;
    private Scanner leitor;
    public Leitor(String path) throws IOException{
        file = new File(path);
        leitor = new Scanner(file);
    }
    public List<ILaMaSerializable> leObjetos() {
        List<ILaMaSerializable> lista = new ArrayList<>();

        while(leitor.hasNext()) {

            String s = leitor.next();

            if(s.equals("Lacaio")) {
                String nome = null;
                UUID id = null;
                int custoMana = 0;
                int ataque = 0;
                int vidaAtual = 0;
                int vidaMaxima = 0;
                HabilidadesLacaio habilidade = null;

                s = leitor.next();
                while(!s.equals("Lacaio")) {

                    if(s.equals("nome")) {
                        nome = leitor.next();
                    } else if (s.equals("id")) {
                        id = UUID.fromString(leitor.next());
                    } else if (s.equals("custoMana")) {
                        custoMana = Integer.valueOf(leitor.next());
                    } else if (s.equals("ataque")) {
                        ataque = Integer.valueOf(leitor.next());
                    } else if (s.equals("vidaAtual")) {
                        vidaAtual = Integer.valueOf(leitor.next());
                    } else if (s.equals("vidaMaxima")) {
                        vidaMaxima = Integer.valueOf(leitor.next());
                    } else if (s.equals("habilidade")) {
                        habilidade = HabilidadesLacaio.valueOf(leitor.next());
                    }

                    s = leitor.next();
                }
                Lacaio lac = new Lacaio(id, nome, custoMana, ataque, vidaAtual, vidaMaxima, habilidade);
                lista.add(lac);

            } else if(s.equals("Buff")) {

                String nome = null;
                UUID id = null;
                int custoMana = 0;
                int aumentoEmVida = 0;
                int aumentoEmAtaque = 0;

                s = leitor.next();

                while(!s.equals("Buff")) {

                    if(s.equals("nome")) {
                        nome = leitor.next();
                    } else if (s.equals("id")) {
                        id = UUID.fromString(leitor.next());
                    } else if (s.equals("custoMana")) {
                        custoMana = Integer.valueOf(leitor.next());
                    } else if (s.equals("aumentoEmAtaque")) {
                        aumentoEmAtaque = Integer.valueOf(leitor.next());
                    } else if (s.equals("aumentoEmVida")) {
                        aumentoEmVida = Integer.valueOf(leitor.next());
                    }

                    s = leitor.next();
                }
                Buff buf = new Buff(id, nome, custoMana, aumentoEmAtaque, aumentoEmVida);
                lista.add(buf);

            } else if(s.equals("Dano")) {
                String nome = null;
                UUID id = null;
                int custoMana = 0;
                int dano = 0;

                s = leitor.next();

                while(!s.equals("Dano")) {

                    if(s.equals("nome")) {
                        nome = leitor.next();
                    } else if (s.equals("id")) {
                        id = UUID.fromString(leitor.next());
                    } else if (s.equals("custoMana")) {
                        custoMana = Integer.valueOf(leitor.next());
                    } else if(s.equals("dano")) {
                        dano = Integer.valueOf(leitor.next());
                    }
                    s = leitor.next();
                }
                Dano dan = new Dano(id, nome, custoMana, dano);
                lista.add(dan);

            } else if(s.equals("DanoArea")) {
                String nome = null;
                UUID id = null;
                int custoMana = 0;
                int dano = 0;

                /*toDO: trecho com problemas*/
                //if(leitor.hasNext()) {
                s = leitor.next();
                //}

                while(!s.equals("DanoArea")) {

                    if(s.equals("nome")) {
                        nome = leitor.next();
                    } else if (s.equals("id")) {
                        id = UUID.fromString(leitor.next());
                    } else if (s.equals("custoMana")) {
                        custoMana = Integer.valueOf(leitor.next());
                    } else if(s.equals("dano")) {
                        dano = Integer.valueOf(leitor.next());
                    }
                    s = leitor.next();
                }
                DanoArea danA = new DanoArea(id, nome, custoMana, dano);
                lista.add(danA);
            }
        }
        return lista;
    }
}
