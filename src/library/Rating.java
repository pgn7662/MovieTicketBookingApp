package library;

public class Rating {
    private final int[] ratingCount;
    private double totalRating;

    Rating(){
        ratingCount = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    double getTotalRating(){
        int totalCount = 0;
        for(int counter = 0;counter < ratingCount.length; counter++){
            totalRating += ((counter+1)*ratingCount[counter]);
            totalCount+=ratingCount[counter];
        }
//        if(totalRating == 0)
//            return 0;
        double rating = totalRating/totalCount;
        totalRating = 0;
        return rating;
    }

    void addRating(int rating){
        ratingCount[rating-1]++;
    }

}
