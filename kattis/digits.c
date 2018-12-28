#include "cs1010.h"

/**
 * Given a number n, return the digit that occurs the most
 * number of time consecutively in this number, breaking
 * ties by preferring the smaller digit.
 *
 * @param[in] n The number to find longest consecutive digit in.
 * @return The long consecutive digit.
 */
long longest_consecutive_digits(long n) 
{
  long longest_count = -1;
  long longest_digit;
  long current_count = 0;
  long current_digit = n % 10;

  do {
    // Increase the counter if we see the same digit.
    // Otherwise reset counter to 1.
    if (n % 10 == current_digit) {
      current_count += 1;
    } else {
      current_count = 1;
    }

    // Checks if we find a longer (or equally long) 
    // consecutive sequence.  Update longest_digit
    // and longest_count if so.
    if (current_count > longest_count) {
      longest_digit = current_digit;
      longest_count = current_count;
    } else if (current_count == longest_count) {
      if (current_digit < longest_digit) {
        longest_digit = current_digit;
      }
    }

    // Update the current digit to the last digit of n
    // and shorten n by one digit.
    current_digit = n % 10;
    n = n / 10;
  } while (n > 0);
  return longest_digit;
}

int main()
{
  long n = cs1010_read_long();
  cs1010_println_long(longest_consecutive_digits(n));
}