#lang racket

(require racket/contract)

(provide ordered? list-of-numbers? all-less-than? all-greater-than? all-less-than/greater-than?)

(define (ordered? lst)
  (if (<= (length lst) 2)
      #t
      (and (<= (car lst) (cadr lst))
           (ordered? (cdr lst)))))

(define (list-of-numbers? lst)
  (cond [(null? lst) #t]
        [(not (number? (car lst))) #f]
        [else (list-of-numbers? (cdr lst))]))

(define (all-less-than? pivot)
  (lambda (lst)
    (cond [(not (list? lst)) #f]
          [(<= (length lst) 2) #t]
          [else (and
                 (< (car lst) pivot)
                 ((all-less-than? pivot) (cdr lst)))])))

(define (all-greater-than? pivot)
  (lambda (lst)
    (cond [(not (list? lst)) #f]
          [(<= (length lst) 2) #t]
          [else (and
                 (>= (car lst) pivot)
                 ((all-greater-than? pivot) (cdr lst)))])))

(define (all-less-than/greater-than? pivot)
  (lambda (p)
    (and ((all-less-than? pivot) (car p))
         ((all-greater-than? pivot) (cdr p)))))

