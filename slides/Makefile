DOCNAME = Slides
TARGET = slides.pdf
TEAM   = yaka
THEME  = yaka2018

RM           ?= rm -f
BUTLER       ?= ../tools/butler/butler.py
BUTLERFLAGS   = --docname="${DOCNAME}" --theme=${THEME} --doctype=slide --team=${TEAM}
LATEX         = xelatex
LATEXFLAGS    = -halt-on-error -no-shell-escape


all: ${TARGET}

clean:
	${RM} ${TARGET}
	${RM} *.aux *.log *.out *.toc

.rst.tex:
	${BUTLER} ${BUTLERFLAGS} $< -o $@

.tex.pdf:
	${LATEX} ${LATEXFLAGS} $<
	${LATEX} ${LATEXFLAGS} -jobname=${@:%.pdf=%} $< >/dev/null
	${LATEX} ${LATEXFLAGS} -jobname=${@:%.pdf=%} $< >/dev/null
	${RM} ${@:.pdf=.aux} ${@:.pdf=.log} ${@:.pdf=.out} ${@:.pdf=.toc} \
	  ${@:.pdf=.nav} ${@:.pdf=.snm} ${@:.pdf=.vrb}

.SUFFIXES: .rst .tex .pdf

.PHONY: all clean
