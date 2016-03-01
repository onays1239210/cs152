#lang racket

(define (quicksort lst)
  (cond [(null? lst) '()]
        [else
         (let* ([pivot (car lst)]
                [p (partition (cdr lst) pivot '() '())]
                [left  (quicksort (car p))]
                [right (quicksort (cdr p))])
           (cons pivot (append left right)))])) ;; BROKEN     

;; Differs from your traditional quicksort,
;; since the sort is not done in place,
;; and also we do not return a partioned list.
;; Instead we return 2 lists.
(define (partition lst pivot left right)
  (if (null? lst)
    (cons left right)
    (let ([x (car lst)]
          [rest (cdr lst)])
      (if (> x pivot) ;; BROKEN
        (partition rest pivot (cons x left) right)
        (partition rest pivot left (cons x right))))))

;; Incorrectly returns '(9 33 45 33 16 8 0 1)
(quicksort '(9 33 0 1 45 16 8 33))
(quicksort '(-19 -3 -45 -8 -999 -2 -1))
(quicksort '(1))
(quicksort '())
