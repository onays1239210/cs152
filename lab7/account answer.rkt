#lang racket

;;Kun Su
(require racket/contract)

(struct account (balance))

(provide new-account balance withdraw deposit account positive-number?)

(define (positive-number? num)
  (if (< 0 num)
      #t
      #f
  ))

(define (positive-reminder? acc )
  (if (< 0 (account-balance acc) )
      #t
      #f
  ))

;; A new, empty account
(define new-account (account 0))

;; Get the current balance
(define/contract (balance acc)
  (account? . -> . number?)
  (account-balance acc))

;; Withdraw funds from an account
(define/contract (withdraw acc amt)
  (account? positive-number? . -> . (and/c account? positive-reminder?))
  (account (- (account-balance acc) amt)))

;; Add funds to an account
(define/contract (deposit acc amt)
  (account? positive-number? . -> . account?)
  (account (+ (account-balance acc) amt)))


