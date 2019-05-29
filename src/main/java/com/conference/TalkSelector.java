package com.conference;

import com.conference.domain.Talk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TalkSelector {

    private Talk[] proposedTalks;
    private int[] visitedIndexes; //to mark indices of proposedTalks array that form the desired subset
    private int maximumSum = 0; //maximum of all nodeMax values (maximum sum closer or equal to to the session duration)

    private List<Talk[]> candidateSubsets; //accumulates the subsets whose sum is closer to the desired duration

    TalkSelector(Talk[] proposedTalks){

        this.proposedTalks = proposedTalks;
        this.visitedIndexes = new int[proposedTalks.length];
        candidateSubsets = new ArrayList<>();
    }

    /**
     *
     * @param durationInMinutes
     * @return
     */
    public Talk[] selectTalksForDuration(int durationInMinutes){
        //sort it in reverse so that, we can stop following futile nodes (backtrack) faster (than sorted in asc order).
        Arrays.sort(proposedTalks, Collections.reverseOrder());

        int totalDurationInMinutes = Arrays.stream(proposedTalks).mapToInt(Talk::getMinutes).sum();

        selectTalksForDuration(0, 0, 0, totalDurationInMinutes, durationInMinutes);

        if(candidateSubsets.size() == 0)  return null;
        //the last array in the list is the one which is the closest sum to the timeLimit
        return candidateSubsets.get(candidateSubsets.size() -1);
    }

    /**
     * The problem we try to solve here is similar to Knapsack problem. I used "Backtracking" technique to switch off
     * the nodes that don't get us close to the timeLimit (desired number) or those that exceed the desired number
     *
     * Let A = [a1, a2, a3, a4];
     * K = Desired number (timeLimitInMinutes)
     *
     * Start from a1, and we follow two paths
     * path 1: create a subset that includes a1
     * path 2: create a subset that excludes a2
     *
     * path 1.1 -> if sum including a1 < desiredNumber include a1 in the subset (switch it on by assigning '1' in the 'visitedIndexes'
     *      path 1.1.1 -> If sum including next element get closer to the desiredNumber include that element and get deeper (make recursive call)
     *      path 1.1.2 -> If sum including next element exceed desiredNumber,
     *          exclude the element from the subset (switch it off by assigning '0' in the corresponding position in the 'visitedIndexes')
     *              and continue doing so until we find an element that adds up to the nodeSum that gets closer to the desired number.
     *
     * path 1.2 -> if sum including a1 > desiredNumber exclude a1 from the subset (switch it off by assigning '0' in the 'visitedIndexes') and go to the next index
     *
     *
     * @param index
     * @param nodeSum
     * @param nodeMax
     * @param remainingSum
     * @param timeLimitInMinutes
     */
    private void selectTalksForDuration(int index, int nodeSum, int nodeMax, int remainingSum, int timeLimitInMinutes) {

        //if we already got a perfect sum from previous recursions, we don't need to explore other nodes.
        if(maximumSum == timeLimitInMinutes) return;

        visitedIndexes[index] = 1;

        int currentSum = proposedTalks[index].getMinutes() + nodeSum;
        //path 1
        if(currentSum > nodeMax){
            //path 1.1
            if(currentSum <= timeLimitInMinutes) {

                nodeMax = currentSum;
                int counter = 0;
                final int noOfMarkedIndexes = (int) Arrays.stream(visitedIndexes).limit(index + 1).filter(visitedIndexesElement -> visitedIndexesElement == 1).count();
                Talk[] closestSumSet = new Talk[noOfMarkedIndexes];

                for(int i = 0; i <= index; i++) {

                    if(visitedIndexes[i] == 1) {

                        closestSumSet[counter++] = proposedTalks[i];
                    }
                }

                candidateSubsets.add(closestSumSet);
                if(currentSum > maximumSum) maximumSum = currentSum;

            } else {//path 1.2

                visitedIndexes[index] = 0; //exclude the current element at index since when added to the nodeSum it exceeds the desiredNumber
                if(index + 1 < proposedTalks.length){

                    selectTalksForDuration(index + 1, nodeSum, nodeMax, remainingSum - proposedTalks[index].getMinutes(), timeLimitInMinutes);
                }
            }
        }

        if(index + 1 < proposedTalks.length){

            int nextSum = currentSum + proposedTalks[index + 1].getMinutes();
            //path 1.1.1
            if(nextSum >= nodeMax && nextSum <= timeLimitInMinutes ){

                selectTalksForDuration(index + 1, currentSum, nodeMax, remainingSum - proposedTalks[index].getMinutes(), timeLimitInMinutes);

            } else { //path 1.1.2

                visitedIndexes[index + 1] = 0; //exclude element at index+1 since when added to the currentSum it exceeds the desiredNumber
                int tempIndex = index + 2;

                while(tempIndex < proposedTalks.length ){

                    nextSum = currentSum + proposedTalks[tempIndex].getMinutes();

                    if(nextSum <= timeLimitInMinutes){

                        selectTalksForDuration(tempIndex, currentSum, nodeMax, remainingSum - proposedTalks[index].getMinutes() - proposedTalks[tempIndex].getMinutes(), timeLimitInMinutes);
                        break;

                    } else {

                        visitedIndexes[tempIndex] = 0;  //exclude element at tempIndex since when added to the currentSum it exceeds the desiredNumber
                        remainingSum -= proposedTalks[tempIndex].getMinutes();
                    }

                    tempIndex++;
                }
            }

            nextSum = currentSum + proposedTalks[index + 1].getMinutes();
            //path 2 (the last condition: if after adding the next element, we won't get a value that exceed (or equal to) the already attained maximumSum don't follow the path
            if(nextSum >= nodeMax && nextSum <= timeLimitInMinutes && (nodeSum + remainingSum - proposedTalks[index].getMinutes() >= maximumSum)){

                visitedIndexes[index] = 0; //exclude element at index to explore the next branch without element at index
                selectTalksForDuration(index + 1, nodeSum, nodeMax, remainingSum - proposedTalks[index].getMinutes(), timeLimitInMinutes);
            }
        }
    }

    /**
     *
     * @return
     */
    public List<Talk[]> getCandidateSubsets() {

        return candidateSubsets;
    }
}
