#lang racket

;; Reverse the order of items in a list.
(define (reverse lst)
  (cond [(empty? lst) '( )]
        [else (append (reverse (cdr lst)) (list (car lst)))]))

;; Should return '(4 3 2 1)
(reverse '(1 2 3 4))



;; Join two lists together by adding their elements.
;; If one list is longer than the other, add the
;; additional elements to the end of the list.
(define (add-two-lists lst1 lst2)
  (cond [(and (empty? lst1) (empty? lst2)) '()]
        [(< (length lst1) (length lst2)) (add-two-lists (append lst1 '(0)) lst2)]
        [(> (length lst1) (length lst2)) (add-two-lists lst1 (append lst2 '(0)))]
        [else (append (list (+ (car lst1) (car lst2))) (add-two-lists (cdr lst1) (cdr lst2)))]))

(define (length lis)
   (cond ((null? lis)
          0)
         (else
          (+ 1 (length (cdr lis))))))

;; Should return '(10 2 5 5 5)
(add-two-lists '(1 2 3 4 5) '(9 0 2 1 0))

;; Should return '(8 6 1)
(add-two-lists '(5 4) '(3 2 1))

;; Should return '(15 17 18 16)
(add-two-lists '(13 14 15 16) '(2 3 3))



;; Return the list with only positive number included.
(define (positive-nums-only lst)
  (cond [(empty? lst) '()]
        [(> (car lst) 0) (cons (car lst) (positive-nums-only (cdr lst)))]
        [else (positive-nums-only (cdr lst))]))

(positive-nums-only '(1 2 -4 90 -4))




;; Create an if statement that supports JavaScripts "falsey" values.
;; Namely, 0, null, and "" should all be treated like #f.
(define (js-if condition then-block else-block)
  (if condition ;; need to fix this line
      else-block
      then-block))

;; Should be "truthy"
(js-if #t #t #f)
(js-if 1 1 0)
(js-if '(1 2 3) #t #f)
(js-if "hello" "true" "false")

;; Should be "falsey"
(js-if #f #t #f)
(js-if 0 1 0)
(js-if null #t #f)
(js-if "" "true" "false")
