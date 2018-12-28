#include <stdio.h>
void foo(double *ptr, double trouble) {
  ptr = &trouble;
  *ptr = 10.0;
}

int main() {
  double *ptr;
  double x = -3.0;
  double y = 7.0;
  ptr = &y;

  foo(ptr, x);

  printf("%d\n", x);
  printf("%d\n", y);
}
