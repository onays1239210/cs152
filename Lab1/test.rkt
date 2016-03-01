#lang racket
(define (add-one-to each-elem lst)
  (cond [(empty? lst) '()]
        [else(cons (+ 1 (car lst)) (add-one-to-eaech-elem (cdr lst)))]))

(define (do-to-each-elem fn lst)
  (cond [(empty? lst) '()]
        [else (cons (fn (car lst)) (do-to-each-elem fn (cdr lst)))]))
(map (λ(x) (- x 1)) lst)

(define (sum-list lst)
  (cond [(empty? lst) 0]
        [else (+ (car lst) (sum-list (cdr lst)))]))


(define (mult-list lst)
  (cond [(empty? lst) 1]
        [else (* (car lst) (mult-lst (cdr lst)))]))

(define (combine-list fn acc lst)
  (cond [(empty? lst) acc]
        [else (combine-list fn (fn acc (car lst))
                            (cdr lst))]))
(combine-list (λ(a y) (+ a y)) 0 '(1 2 3 4 5))
fold l  ;; processing the list backward