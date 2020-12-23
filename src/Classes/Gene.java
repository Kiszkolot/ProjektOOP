package Classes;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Gene {
    private final ArrayList<Integer> genotype;
    private final int range;
    private final int size;

    public Gene(int range, int size){
        ArrayList<Integer> geny = new ArrayList<>();
        this.range = range;
        this.size = size;
        for(int i=0; i<range;i++){
            geny.add(i);
        }
        for(int i = range; i< size;i++){
            geny.add(new Random().nextInt(range-1));
        }
        Collections.sort(geny);
        this.genotype = geny;
    }

    public void replaceRandomGene(int k){
        int n = this.genotype.size();
        int a = new Random().nextInt(n);
        this.genotype.set(a, k);
    }

    public int getRandomGene(){
        int n = this.genotype.size();
        int a = new Random().nextInt(n);
        return this.getGenotype().get(a);
    }

    public ArrayList<Integer> getGenotype() {
        return this.genotype;
    }

    public int getRange() {return this.range;}

    public int getSize() {return this.size;}

    public Gene(ArrayList<Integer> one,ArrayList<Integer> other, int size,int range){
        this.range = range;
        this.size= size;
        ArrayList<Integer> geny = new ArrayList<>();
        for(int i=0; i<range;i++){
            geny.add(i);
            //zapewnienie że będziemy mieli wszystkie geny
        }
        int firstGeneBoundary = new Random().nextInt((size-range)+1)+range;
        int secondGeneBoundary = new Random().nextInt((size-firstGeneBoundary)+1)+firstGeneBoundary;
        //znajdowanie miejsc podziału
        Collections.shuffle(one);
        Collections.shuffle(other);
        //mieszanie tablic startowych, żeby wyniki były losowe
        ArrayList<Integer> partOne = (ArrayList<Integer>) one.subList(range,firstGeneBoundary);
        ArrayList<Integer> partTwo = (ArrayList<Integer>) other.subList(firstGeneBoundary,secondGeneBoundary);
        ArrayList<Integer> partThree = (ArrayList<Integer>) one.subList(secondGeneBoundary,size);
        //wybieranie części
        geny.addAll(partOne);
        geny.addAll(partTwo);
        geny.addAll(partThree);
        //dodawanie części
        Collections.sort(geny);
        this.genotype = geny;
    }

    public int check_validity(int length, int range){
        int[] exists = new int[range];
        int ret = -1;
        for(int i=0;i<range;i++){
            exists[i] = 0;
        }
        for(int i=0;i<length;i++){
            exists[this.genotype.get(i)]+=1;
        }
        for(int i=0;i<range;i++){
            if(exists[i]==0){
                ret = i;
            }
        }
        return ret;
    }

    public Gene(Gene parent1, Gene parent2){
        this.range = parent1.getRange();
        this.size= parent2.getSize();
        ArrayList<Integer> geny = new ArrayList<>();

        int firstGeneBoundary = new Random().nextInt(size);
        int secondGeneBoundary = new Random().nextInt((size-firstGeneBoundary)+1)+firstGeneBoundary;

        ArrayList<Integer> partOne = (ArrayList<Integer>) parent1.getGenotype().subList(0,firstGeneBoundary);
        ArrayList<Integer> partTwo = (ArrayList<Integer>) parent2.getGenotype().subList(firstGeneBoundary,secondGeneBoundary);
        ArrayList<Integer> partThree = (ArrayList<Integer>) parent1.getGenotype().subList(secondGeneBoundary,size);

        geny.addAll(partOne);
        geny.addAll(partTwo);
        geny.addAll(partThree);

        Collections.sort(geny);
        this.genotype = geny;
        int k = this.check_validity(32,8);

        while(k!=-1){
            this.replaceRandomGene(k);
            k = this.check_validity(32,8);
        }

    }
}

