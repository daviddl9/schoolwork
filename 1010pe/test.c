#include <stdio.h>
#include <stdbool.h>

bool isHappy(int x);
int computeHappyNumbers(int lower, int upper);
int calculateSumOfSquares(int x);
bool isContained(int x);

int main(void){
	int lower1, upper1, lower2, upper2, number1, number2;

	printf("Enter first range: ");
   scanf("%d", &lower1);
   scanf("%d", &upper1);

	printf("Enter second range: ");
   scanf("%d", &lower2);
   scanf("%d", &upper2);
   
   number1 = computeHappyNumbers(lower1, upper1);
   number2 = computeHappyNumbers(lower2, upper2);

	printf("The numbers of happy numbers in the two ranges are: %d %d\n", number1, number2);

   if (number1 > number2)
	   printf("There are more happy numbers in the first range.\n");
   
   if (number2 > number1)
	   printf("There are more happy numbers in the second range.\n");

	if (number1 == number2)
      printf("The numbers of happy numbers in both ranges are the same.\n");

	return 0;
}

int computeHappyNumbers(int lower, int upper) {
   int count = 0;
   int i;
   for (i = lower; i <= upper; i++) {
      if (isHappy(i)) count++;
   }
   return count;
}

bool isHappy(int x) {
   // Algorithm:
   // 1. Calculate the sum of squares, until it reaches one of the terminating digits. 
   // 2. If the terminating digit is one, return true, if not, return false. 
   int temp; 
   temp = calculateSumOfSquares(x);
   while (!isContained(temp)) {
      temp = calculateSumOfSquares(temp);
   }
   // Invariant here is that temp has reached one of the terminating numbers
   if (temp == 1) return true;
   return false;
}

bool isContained(int x) {
   int arr[] = {0, 1, 4, 16, 20, 37, 42, 58, 89, 145};
   int i;
   for (i = 0; i < 9; i++) {
      if (x == arr[i]) return true;
   }
   return false;
}

int calculateSumOfSquares(int x) {
   int sum = 0;
   int last_digit;
   int iteration = 0;
   while (x >= 10) {
      iteration++;
      last_digit = x % 10;
      sum += last_digit * last_digit;
      x /= 10;
   }
   sum += x * x;
   return sum;
}

