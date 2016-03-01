#lang racket
(define (max-num lst)
      (cond [(empty? lst) (error "err")]
            [(= 1 (length lst)) (car lst)]
            [else (if (> (car lst) (max-num (cdr lst)))
                       (car lst)
                       (max-num (cdr lst)))]
            )
            )
;;(define (fizzbuzz n)
 ;; (print (fizzbuzzl 1 n)))
;;(define (fizzbuzzl i n)
 ;; (if (= i n)
 ;;     " "
  ;;    (string-append
  ;;     (cond [(and (is-divisible-by i 3) (is-divisible-by i 5)) "fizzbazz"]
 ;;            [(is-divisible-by i 3) "fizz"]
   ;;          [(is-divisible-by i 5) "buzz"]
     ;;        [else (number->string i)])
       ;;      " "
         ;;    (fizzbuzzl (+ 1 i) n))))


(max-num '(9 0 42 1 6)) ;; evaluates to 42
(max-num '(-2 -8 -865)) ;; evaluates to -2
