package com.mygdx.game.cardGame;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
       final private int cards_width;
       final private int cards_height;
       private int size;
       public int opened;
       boolean ended;
       int completed;
       Random random = new Random();
       private int type;
       private ArrayList<Card> cards = new ArrayList<Card>();
       ArrayList<Integer> types = new ArrayList<Integer>();
       int w,h;

    public Deck(int cards_width, int cards_height){
        this.cards_width = cards_width;
        this.cards_height = cards_height;
        opened = 0;
        completed = 0;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
    }

    public void sortCards(){
        if(cards.size() != 0){
            cards.clear();
            completed = 0;
        }
        type = random.nextInt(3);

        for(int i = 0; i < 6; i++){
            types.add((int) i);
        }
        if(type == 0){
            size = 8;

            for(int i = 0; types.size() != 4; i++){
                types.remove(random.nextInt(types.size() - i));
            }
            types.addAll(types);

            int cur_size = size;
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 4; j++){
                    int index = random.nextInt(cur_size);
                    cards.add(new Card((int)(w / 3.76) + (cards_width + (int)(w / 32)) * j, (int)(h / 6) + (cards_height + (int)(h / 18)) * i,types.get(index)));
                    types.remove(index);
                    cur_size--;
                }
            }
        }

        else if(type == 1 ){
            size = 10;

            for(int i = 0; types.size() != 5; i++){
                types.remove(random.nextInt(types.size() - i));
            }
            types.addAll(types);

            int cur_size = size;
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 3; j++){
                    int index = random.nextInt(cur_size);
                    cards.add(new Card((int)(w / 4.57) + (cards_width * 2 + (int)(w / 64)) * j, (int)(h / 18) + (cards_height * 2 + (int)(h / 36)) * i,types.get(index)));
                    types.remove(index);
                    cur_size--;
                }
            }
            for(int i = 0; i < 4; i++){
                int index = random.nextInt(cur_size);
                cards.add(new Card((int)(w / 9.14) + (cards_width * 2 + (int)(w / 64)) * i, (int)(h / 18) + (cards_height + (int)(h / 72)),types.get(index)));
                types.remove(index);
                cur_size--;
            }
        }
        else if(type == 2){
            size = 12;
            for(int i = 0; types.size() != 6; i++){
                types.remove(random.nextInt(types.size() - i));
            }
            types.addAll(types);

            int cur_size = size;

            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 4; j++){
                    int index = random.nextInt(cur_size);
                    cards.add(new Card((int)(w / 3.76) + (cards_width + (int)(w / 64)) * j, (int)(h / 24) + (cards_height + (int)(h / 72)) * i,types.get(index)));
                    types.remove(index);
                    cur_size--;
                }
            }
        }
    }


    public boolean checkIsSame(long timeLock){
        for (int i = 0; i < size; i++){
            Card card = cards.get(i);
            if(card.isLast && timeLock < System.currentTimeMillis()){
                card.isLast = false;
                card.setPressed(false);
                int index = 0;
                for(int b = 0; b < size; b++){
                    if(cards.get(b).isPressed()){
                        index = b;
                        break;
                    }
                }
                if(card.getType() == cards.get(index).getType()){
                    cards.get(index).isComplete = true;
                    card.isComplete = true;
                    completed += 2;
                    card.setAnimating(false);
                    cards.get(index).setAnimating(false);
                    cards.get(index).setPressed(false);
                    return true;
                }
                else{
                    cards.get(index).isReverse = true;
                    CardGame.cardFlip.play(1f);
                    card.isReverse = true;
                    card.setAnimating(true);
                    cards.get(index).setAnimating(true);

                }
                card.stateTime = 0;
                cards.get(index).stateTime = 0;

                opened = 0;
            }
            else if(card.isLast && !card.isAnimating){
                int index = 0;
                card.setPressed(false);
                for(int b = 0; b < size; b++){
                    if(cards.get(b).isPressed()){
                        index = b;
                        break;
                    }
                }
                if(card.getType() == cards.get(index).getType()){
                    card.isLast = false;
                    cards.get(index).isComplete = true;
                    card.isComplete = true;
                    completed += 2;
                    card.setAnimating(false);
                    cards.get(index).setAnimating(false);
                    cards.get(index).setPressed(false);
                    card.stateTime = 0;
                    cards.get(index).stateTime = 0;
                    opened = 0;
                    return true;
                }
                else{
                    card.setPressed(true);
                }
            }
        }
        return false;

    }


    public int getCards_width() {
        return cards_width;
    }

    public int getCards_height() {
        return cards_height;
    }

    public int getAmountOfCards() {
        return cards.size();
    }

    public void setAmountOfCards(int amountOfCards) {
        this.size = amountOfCards;
    }

    public Card getCard(int i){
        return cards.get(i);
    }
    public void removeCard(int i){
        cards.remove(i);
    }

        class Card{
            private boolean isPressed;
            public boolean isLast;
            float stateTime;
            private boolean isAnimating;
            boolean isReverse;
            boolean isComplete;
            private int x;
            private int y;
            private int type;

            public Card(int x, int y, int type){
                isPressed = false;
                isLast = false;
                isAnimating = false;
                isComplete = false;
                isReverse = false;
                stateTime = 0f;
                this.x = x;
                this.y = y;
                this.type = type;
            }

            public int getX() {
                return x;
            }

            public int getY() {
                return y;
            }

            public int getType() {
                return type;
            }

            public void setPressed(boolean pressed) {
                isPressed = pressed;
            }

            public boolean isPressed() {
                return isPressed;
            }

            public void setAnimating(boolean animating) {
                isAnimating = animating;
            }

            public boolean isAnimating() {
                return isAnimating;
            }



            public void setType(int type) {
            this.type = type;
        }
    }
}
