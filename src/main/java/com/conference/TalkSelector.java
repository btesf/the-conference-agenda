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

    public TalkSelector(Talk[] proposedTalks){

        this.proposedTalks = proposedTalks;
        this.visitedIndexes = new int[proposedTalks.length];
        candidateSubsets = new ArrayList<>();
    }

    public Talk[] selectTalksForDuration(int durationInMinutes){
        //sort it in reverse so that, we can stop following futile nodes (backtrack) faster (than sorted in asc order).
        Arrays.sort(proposedTalks, Collections.reverseOrder());

        int totalDurationInMinutes = Arrays.stream(proposedTalks).mapToInt(Talk::getMinutes).sum();

        selectTalksForDuration(0, 0, 0, totalDurationInMinutes, durationInMinutes);

        if(candidateSubsets.size() == 0)  return null;
        //the last array in the list is the one which is the closest sum to the timeLimit
        return candidateSubsets.get(candidateSubsets.size() -1);
    }

    
    public void selectTalksForDuration(int index, int nodeSum, int nodeMax, int remainingSum, int timeLimitInMinutes) {

        if(maximumSum == timeLimitInMinutes) return;

        visitedIndexes[index] = 1;

        int currentSum = proposedTalks[index].getMinutes() + nodeSum;

        if(currentSum > nodeMax){

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

            } else {

                visitedIndexes[index] = 0;
                if(index + 1 < proposedTalks.length){

                    selectTalksForDuration(index + 1, nodeSum, nodeMax, remainingSum - proposedTalks[index].getMinutes(), timeLimitInMinutes);
                }
            }
        }

        if(index + 1 < proposedTalks.length){

            int nextSum = currentSum + proposedTalks[index + 1].getMinutes();

            if(nextSum >= nodeMax && nextSum <= timeLimitInMinutes ){

                selectTalksForDuration(index + 1, currentSum, nodeMax, remainingSum - proposedTalks[index].getMinutes(), timeLimitInMinutes);

            } else {

                visitedIndexes[index + 1] = 0;
                int tempIndex = index + 2;

                while(tempIndex < proposedTalks.length ){

                    nextSum = currentSum + proposedTalks[tempIndex].getMinutes();

                    if(nextSum <= timeLimitInMinutes){

                        selectTalksForDuration(tempIndex, currentSum, nodeMax, remainingSum - proposedTalks[index].getMinutes() - proposedTalks[tempIndex].getMinutes(), timeLimitInMinutes);
                        break;

                    } else {

                        visitedIndexes[tempIndex] = 0;
                        remainingSum -= proposedTalks[tempIndex].getMinutes();
                    }

                    tempIndex++;
                }
            }

            nextSum = currentSum + proposedTalks[index + 1].getMinutes();

            if(nextSum >= nodeMax && nextSum <= timeLimitInMinutes && (nodeSum + remainingSum - proposedTalks[index].getMinutes() >= maximumSum) ){

                visitedIndexes[index] = 0;
                selectTalksForDuration(index + 1, nodeSum, nodeMax, remainingSum - proposedTalks[index].getMinutes(), timeLimitInMinutes);
            }
        }
    }

    public List<Talk[]> getCandidateSubsets() {

        return candidateSubsets;
    }
}
