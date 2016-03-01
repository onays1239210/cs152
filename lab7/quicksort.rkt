#lang racket

(require racket/contract)
(require "my-contracts.rkt")

(define/contract (quicksort lst)
  (list? . -> . (and/c list? ordered?))
  (cond [(null? lst) '()]
        [else
         (let* ([pivot (car lst)]
                [p (partition (cdr lst) pivot '() '())]
                [left  (quicksort (car p))]
                [right (quicksort (cdr p))])
           (append left (cons pivot right)))]))
           ;;(cons pivot (append left right)))])) ;; BROKEN approach one
     

;; Differs from your traditional quicksort,
;; since the sort is not done in place,
;; and also we do not return a partioned list.
;; Instead we return 2 lists.
(define/contract (partition lst pivot left right)
  ;; What we might try at first:
  ;(-> list-of-numbers? number? (less-than-pivot? pivot) (more-than-pivot? pivot)
  ;    (and/c pair? (less-than/greater-than-pivot? pivot)))
  (->i ([the-list list-of-numbers?]
        [the-pivot number?]
        [left-list (the-pivot) (all-less-than? the-pivot)]
        [right-list (the-pivot) (all-greater-than? the-pivot)])
       [result-list (the-pivot) (all-less-than/greater-than? the-pivot)])
  (if (null? lst)
    (cons left right)
    (let ([x (car lst)]
          [rest (cdr lst)])
      (if (< x pivot)
      ;;(if (> x pivot) ;; BROKEN
        (partition rest pivot (cons x left) right)
        (partition rest pivot left (cons x right))))))

(quicksort '(9 33 0 1 45 16 8 33))
(quicksort '(-19 -3 -45 -8 -999 -2 -1))
(quicksort '(1))
(quicksort '())
