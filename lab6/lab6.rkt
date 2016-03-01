#lang racket

(define x 99)

(define-syntax-rule (switch x)
  (cond [(= x 3) (displayln "x is 3")]
        [(= x 4) (displayln "x is 4")]
        [(= x 5) (displayln "x is 5")]
        [else (displayln "none of the above")]))

(switch x)
;why

(define-syntax switch
  (syntax-rule ()
               {(switch v) (void)}
               {(switch v ["default body"]) body}
               {(switch v [vall body1] rest-cases ...)
                (if (equ? v vall)
                    body1
                    (switch v rest-cases ...))}))
