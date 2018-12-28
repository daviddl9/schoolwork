/*************************************
* Lab 1 Exercise 3
* Name     : D David Livingston
* Matric No: A0168218Y
* Lab Group: 4
*************************************/

#include <stdio.h>
#include <math.h>

//Datatype Declarations
int (*arithFuncPtr[4])(int, int);


//Function Prototypes
int summation(int x, int y);
int GCD(int x, int y);
int power(int x, int y);
int removeFactor(int x, int y);


int main()
{
    int a, b, optype;
    char *operations[4];
    operations[0] = "lumos";
    operations[1] = "alohomora";
    operations[2] = "expelliarmus";
    operations[3] = "sonorus";

    arithFuncPtr[0] = summation;
    arithFuncPtr[1] = GCD;
    arithFuncPtr[2] = removeFactor;
    arithFuncPtr[3] = power;

    while (scanf("%i %i %i", &optype, &a, &b) != EOF) {
        printf("%s %i\n", operations[optype-1], (*arithFuncPtr[optype-1])(a,b));
    }

    return 0;
}

// Removes all factors y from x.
int removeFactor(int x, int y) {
    while (x%y == 0) {
        x /= y;
    }
    return x;
}

// Returns x^y
int power(int x, int y) {
    return (int) pow(x, y);
}

// Returns the sum of numbers from x to y.
int summation(int x, int y) {
    int sum = 0;
    int i;
    for (i=x; i<=y; i++) {
        sum += i;
    }
    return sum;
}

// Returns the GCD of x and y.
int GCD(int x, int y) {
    int i, gcd;
    for (i=1; i<=x && i<=y; i++) {
        if (x%i == 0 && y%i == 0) {
            gcd = i;
        }
    }
    return gcd;
}
