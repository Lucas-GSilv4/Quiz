package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String caminhoArquivo = "C:/Users/dell/IdeaProjects/Quiz/src/main/java/org/example/Perguntas e Respostas.csv";
        int totalPerguntas = 0;

        try (BufferedReader brCount = new BufferedReader(new FileReader(caminhoArquivo))) {
            while (brCount.readLine() != null) {
                totalPerguntas++;
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }

        String[][] quiz = new String[totalPerguntas][5];

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int i = 0;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\\|");
                if (partes.length == 5) {
                    quiz[i] = partes;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner read = new Scanner(System.in);
        int pontuacao = 0;
        int erros = 0;

        int[] ordemPerguntas = gerarVetorSequencial(totalPerguntas);
        embaralharVetor(ordemPerguntas);

        for (int i = 0; i < totalPerguntas; i++) {
            int indicePergunta = ordemPerguntas[i];
            String pergunta = quiz[indicePergunta][0];
            String respostaCorreta = quiz[indicePergunta][1];

            String[] opcoes = new String[4];
            for (int j = 0; j < 4; j++) {
                opcoes[j] = quiz[indicePergunta][j + 1];
            }

            embaralharOpcoes(opcoes);

            System.out.println("\nPergunta " + (i + 1) + ": " + pergunta);
            for (int j = 0; j < 4; j++) {
                System.out.println((j + 1) + ") " + opcoes[j]);
            }

            System.out.print("Sua resposta (1-4): ");
            int escolha = read.nextInt();

            if (opcoes[escolha - 1].equals(respostaCorreta)) {
                System.out.println("Correto!");
                pontuacao++;
            } else {
                System.out.println("Errado! A correta era: " + respostaCorreta);
                erros++;
                System.out.println("Total de erros: "+ erros);

                if (erros == 5){
                    break;
                }
            }
        }

        System.out.println("\n--- FIM DO JOGO ---");
        System.out.println("Sua pontuação final: " + pontuacao + "/" + totalPerguntas);
    }

    public static int[] gerarVetorSequencial(int n) {
        int[] v = new int[n];
        for (int i = 0; i < n; i++) v[i] = i;
        return v;
    }

    public static void embaralharVetor(int[] v) {
        for (int i = 0; i < v.length; i++) {
            int r = (int) (Math.random() * v.length);
            int temp = v[i];
            v[i] = v[r];
            v[r] = temp;
        }
    }

    public static void embaralharOpcoes(String[] v) {
        for (int i = 0; i < v.length; i++) {
            int r = (int) (Math.random() * v.length);
            String temp = v[i];
            v[i] = v[r];
            v[r] = temp;
        }
    }
}