SPHINX_FLAGS = -T -E
BUILDDIR = _build
SLIDESDIR = slides

all: all-tex

all-tex:
	sphinx-build -M latex . $(BUILDDIR) $(SPHINX_FLAGS)

%-pdf: %-tex
	$(MAKE) -C $(BUILDDIR)/latex all-pdf

slides:
	$(MAKE) -C $(SLIDESDIR) all

clean:
	$(RM) -r $(BUILDDIR)
	$(MAKE) -C $(SLIDESDIR) clean

.PHONY: slides
